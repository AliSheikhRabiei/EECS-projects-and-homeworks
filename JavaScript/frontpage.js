function p00() {
  var num = parseInt(prompt("Please enter a positive integer:"));
  var sum = 0;
  for(i=1; i<=num/2; i++){
    if(num%i == 0){
      sum=sum+i;
    }
  }
  if(sum==num){
    document.getElementById("demo").innerHTML = "yes";
  }
  else{
    document.getElementById("demo").innerHTML = "no.";
  }
  // document.getElementById("demo").innerHTML = "test is ok";
}

function p01() {
  var num = parseInt(prompt("Please enter a positive integer:"));
  var d = 2;
  var prime = "true"
  while(prime=="true" && d<=num/2){
    if(num%d == 0){
      prime="false";
    }
    d++;
  }
  if(prime=="true"){
    document.getElementById("demo").innerHTML = num+" is prime";
  }
  else{
    document.getElementById("demo").innerHTML = num+" is not prime.";
  }
}

function p02() {
  var p=0;
  var n=0;
  var num = parseInt(prompt("Please enter a number:"));
  if(num>0){
    p++;
  }
  if(num<0){
    n++;
  }
  while(num !=0 && p<=2n){
    var num = parseInt(prompt("Please enter a number:"));
    if(num>0){
      p++;
    }
    if(num<0){
      n++;
    }
  }

  document.getElementById("demo").innerHTML = "p: "+p+" n: "+n;
}

function p03() {
  var num = parseInt(prompt("Please enter a number:"));
  var bin = "";
  var n = num;
  while(n != 0){
    if(n%2==0){
      bin="0"+bin;
    }
    else{
      bin="1"+bin;
    }
    n=Math.floor(n/2);
  }
    document.getElementById("demo").innerHTML = num+" in binary is "+bin;

  // document.getElementById("demo").innerHTML = "test is ok";
}

function p04() {

  var cnt1 = 0;
  var cnt2 = 0;
  for(i=1; i<=4; i++){
    var num = parseInt(prompt("Please enter an integer:"));
    if(num<0){
      cnt1++;
    }
  }
  for(i=1; i<=4; i++){
    var num = parseInt(prompt("Please enter an integer:"));
    if(num>0){
      cnt2++;
    }
  }
  if(cnt1==cnt2){
    document.getElementById("demo").innerHTML = "yes";
  }
  else{
    document.getElementById("demo").innerHTML = "no.";
  }
  // document.getElementById("demo").innerHTML = "test is ok";
}

function p05() {
  var i=0;
  var sum=0;
  var num = parseInt(prompt("Please enter a number:"));
  if(i%2==0){
    if(num%2==0){
      sum=sum+num;
    }
  }
  i++;
  while(num != 0){
    var num = parseInt(prompt("Please enter a number:"));
    if(i%2==0){
      if(num%2==0){
        sum=sum+num;
      }
    }
    i++;
  }

  document.getElementById("demo").innerHTML = sum;
}

function prime(num) {
  var d = 2;
  var prime = true;
  while(prime==true && d<=num/2){
    if(num%d == 0){
      prime=false;
    }
    d++;
  }
    return prime;
}

function p06() {
  var num = parseInt(prompt("Please enter a integer:"));
  var bin="";
  // var temp="";
  for(i=2; i<=num; i++){
    if(prime(i)){
  //     temp=pr(num);
      bin=bin+i;
      // bin++;
    }
  }
    document.getElementById("demo").innerHTML = bin;
  // document.getElementById("demo").innerHTML = "test is ok";
}

function has7(num) {
  var p = false;
  var i= 0;
  while(num=!0 && p==false){
    i=n%10;
    n=Math.floor(n/10);
    if(i==7){
      p=true;
    }
  }
    return p;
}

function p07() {
  var i=0;
  var bin="";
  // var temp=0;
  var num = parseInt(prompt("Please enter a number:"));
  if(prime(num)){
    // temp++;
    if(has7(num)){
      bin=bin+num;
      // bin++;
    }
  }
  while(num != 0){
    var num = parseInt(prompt("Please enter a number:"));
    if(prime(num)){
      // temp++;
      if(has7(num)){
        bin=bin+num;
        // bin++;
      }
    }
  }

  document.getElementById("demo").innerHTML = bin;
}
