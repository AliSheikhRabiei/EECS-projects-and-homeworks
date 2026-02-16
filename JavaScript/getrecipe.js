var name="";
var ingredients=[];
var instruction="";

var peg_selected = 0;
var attempt_code;
var current_attempt_id;
var start = new Date();
var btn_initial_top;
var url = "http://localhost:3000/post";


function loadScript(src) {
    let script = document.createElement('script');
    script.src = src;
    document.head.append(script);
}

loadScript('server.js');


function getrecipe() {
  name = document.getElementById("name").value;
  ingredients = document.getElementById("ingredients").value;
  instruction = document.getElementById("instruction").value;
  document.getElementById("mydata").innerHTML = "Your ingredients for "+name+" are: "+ingredients+"\n and Your instruction is: "+instruction;
}

function confirmit() {
  let text = "Press a button!\nEither OK or Cancel.";
  if (confirm(text) == true) {

    //here send ingredients and instruction to server and save it
    var newArrIng=[ingredients];
    var newArrRec=[name, recipe];
    // newArrIng=ingredients.push(newArrIng);
    // newArrRec=recipe.push(newArrRec);
    addArrays(newArrIng,newArrRec);
    text = "Your recipe is saved!";
  } else {
    text = "Your recipe is not saved!";
  }
  document.getElementById("demo").innerHTML = text;
}

function addArrays(clientArray1,clientArray2 ) {
    Promise.all([
      fetch('/add1', {
        method: 'POST',
        body: JSON.stringify(clientArray1),
        headers: {
          'Content-Type': 'application/json'
        }
      }),
      fetch('/add2', {
        method: 'POST',
        body: JSON.stringify(clientArray2),
        headers: {
          'Content-Type': 'application/json'
        }
      })
    ])
    .then(responses => Promise.all(responses.map(response => response.json())))
    .then(([serverArray1, serverArray2]) => console.log({serverArray1, serverArray2}))
    .catch(error => console.error(error));
}
