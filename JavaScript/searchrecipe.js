function loadScript(src) {
    let script = document.createElement('script');
    script.src = src;
    document.head.append(script);
}

loadScript('server.js');


function checkingredients() {
    var i=0;
    var j=0;
    var meal="";
    var temp=[];
    var eatthis="";
    var a = "meat";
    var len = ingredients.length;
    for(i=0;i<len;i++){
      temp.push(0);
    }
    do{
      a = prompt("Please tell us what you have?");
      for(i=0;i<len;i++){
        if((ingredients[i].indexOf(a))>=0){
          temp[i]++;
        }
      }
    }
    while(a!="")

    if(checkForDuplicates(temp)){
      // j = temp.indexOf(Math.max(...temp));
      j = getrandom(indices(temp));
    }
    else{
      j = temp.indexOf(Math.max(...temp));
      // j = getrandom(indices(temp));
    }
    eatthis=recipe[j][0]+": "+recipe[j][1];
    // var arr = [1,0,0];
    //   var k = getrandom(indices(arr));
    //   var l = findDuplicates(arr);
      // j = getrandom(k);

  document.getElementById("demo").innerHTML = eatthis;
}


function findDuplicates(array) {

  // var array = [6, 9, 15, 6, 13, 9, 11, 15];
  var index = 0;
  var newArr = [];
  const length = array.length
    for (let i = 0; i < length-1; i++) {
       for (let j = i + 1; j < length; j++) {
       if (array[i] === array[j]) {
             newArr[index] = array[i];
             index++;
          }
       }
    }
      return newArr;
 }

function indices(arr){
 // var arr = [2,2,3,2,7,3];
 var max = Math.max(...arr);
 var counts = arr.filter(function(yourArr,index, self){
   return !self.indexOf(yourArr)
 });

 var indices = [];

 arr.filter(function(yourArr, index, self){
   if(yourArr == max && counts.length > 1){
    indices.push(index)
  }
 })
 return indices;

}

function getrandom(arr) {
    var randomIndex = Math.floor(Math.random() * arr.length);
    var item = arr[randomIndex];
    return item;
}

function checkForDuplicates(array) {
  let valuesAlreadySeen = []

  for (let i = 0; i < array.length; i++) {
    let value = array[i]
    if (valuesAlreadySeen.indexOf(value) !== -1) {
      return true
    }
    valuesAlreadySeen.push(value)
  }
  return false
}
