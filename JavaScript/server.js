var ingredients=[["oil","meat","salt","pepper","onion","parsley"],["brookly","carrot","celery","green pepper","mashrooms","watter"]];
var recipe=[["meat stew","Heat oil in a large pot or Dutch oven over medium-high heat; add beef and cook until well browned. Dissolve bouillon in 4 cups water and pour into the pot; stir in rosemary, parsley, and pepper. Bring to a boil; reduce heat to low, cover, and simmer for 1 hour. Stir in potatoes, carrots, celery, and onion. Dissolve cornstarch in 2 teaspoons of cold water; stir into stew. Cover and simmer until beef is tender, about 1 hour."],["vegtable soup","put all the vegtable into boiled watter and wait while stearing at them(im not a cheff what did you expect?)"]];


const http = require('http');
const url = require('url');
const qs = require('querystring');

const server = http.createServer((req, res) => {

  const parsedUrl = url.parse(req.url, true);
  const query = parsedUrl.query;

  if (req.method === 'POST' && parsedUrl.pathname === '/add1') {
    let body = '';

    req.on('data', chunk => {
      body += chunk.toString();
    });

    req.on('end', () => {
      const clientArray = JSON.parse(body);
      ingredients = ingredients.push(clientArray);
      res.writeHead(200, {'Content-Type': 'application/json'});
      res.end(JSON.stringify(serverArray1));
    });
  }

  else if (req.method === 'POST' && parsedUrl.pathname === '/add2') {
    let body = '';

    req.on('data', chunk => {
      body += chunk.toString();
    });

    req.on('end', () => {
      const clientArray = JSON.parse(body);
      recipe = recipe.push(clientArray);
      res.writeHead(200, {'Content-Type': 'application/json'});
      res.end(JSON.stringify(serverArray2));
    });
  } else {
    res.writeHead(200, {'Content-Type': 'application/json'});
    res.end(JSON.stringify({
      array1: ingredients,
      array2: recipe
    }));
  }
});

server.listen(3000, () => {
  console.log('Server running at http://localhost:3000/');
});
