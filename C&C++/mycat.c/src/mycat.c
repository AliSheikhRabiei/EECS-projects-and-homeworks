/***************************************
* Author: Sheikh Rabiei, Ali *
* EECS/Prism username: routch *
* Yorku Student #: 218475095 *
* Email: routch@my.yorku.cam *
****************************************/

#include <fcntl.h>
#include <unistd.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// Write exactly n bytes from buf to fd, handling short writes
static int write_all(int fd, const char *buf, size_t n) {
    size_t off = 0;

    while (off < n) {
        ssize_t w = write(fd, buf + off, n - off);
        if (w < 0) {
            if (errno == EINTR) continue;
            return -1;

        }
        off += (size_t)w;
    }

    return 0;

}

// Copy everything from fd -> STDOUT_FILENO using a fixed-size buffer
static int copy_fd_to_stdout(int fd) {
    char buf[4096];
    ssize_t n;

    while ((n = read(fd, buf, sizeof buf)) != 0) {
        if (n < 0) {
            if (errno == EINTR) continue;
            perror("read");
            return 1;

        }
        if (write_all(STDOUT_FILENO, buf, (size_t)n) < 0) {
            perror("write");
            return 1;

        }
    }
    return 0;

}

// Open input according to argv/argc.
static int open_input_from_args(int argc, char *argv[]) {
    if (argc == 1) return STDIN_FILENO;

    int fd = open(argv[1], O_RDONLY);
    if (fd < 0) {
        fprintf(stderr, "open('%s') failed: %s\n", argv[1], strerror(errno));
        return -1;

    }
    return fd;

}

int main(int argc, char *argv[]) {
    if (argc > 2) {
        fprintf(stderr, "Usage: %s [file]\n", argv[0]);
        return 1;
    }

    int fd = open_input_from_args(argc, argv);
    if (fd < 0) return 1;                  // error already reported

    int rc = copy_fd_to_stdout(fd);

    // Close only if we opened a file (never close stdin here).
    if (argc == 2) {
        if (close(fd) < 0) {
            perror("close");
            return 1;

        }
    }
    return rc;

}



