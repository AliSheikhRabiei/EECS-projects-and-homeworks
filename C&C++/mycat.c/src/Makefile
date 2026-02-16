CC = gcc
CFLAGS = -Wall -Wextra -std=c99 -g
TARGET = mycat
SRC = mycat.c

$(TARGET): $(SRC)
$(CC) $(CFLAGS) -o $(TARGET) $(SRC)

run: $(TARGET)
./$(TARGET)

clean: rm -f $(TARGET)

debug: $(TARGET)
gdb ./$(TARGET)

.PHONY: clean debug run
