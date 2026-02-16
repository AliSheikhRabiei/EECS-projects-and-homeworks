function loadScript(src) {
    let script = document.createElement('script');
    script.src = src;
    document.head.append(script);
}

loadScript('server.js');

function printarray(){
  var len = recipe.length;
  var i=0;
  var k="";
  var len = recipe.length;
  const boxWrapper = document.getElementById("box-wrapper");
  for(i=0;i<len;i++){
    const box5 = document.createElement("div");
    box5.innerHTML = '<p>recipe name: '+recipe[i][0]+'<br> ingredients: '+k+'<br> recipe: '+recipe[i][1]+'<br></p>';
    // box5.innerHTML = "test";
    box5.style.backgroundColor = "orange";
    box5.classList.add("box");
    boxWrapper.appendChild(box5);

  }
}
