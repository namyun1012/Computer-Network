const http = require('http');
const fs = require('fs');

function notFound(response) {
    response.writeHead(404, {"content-type":"text/plain"});
    response.write("404 ERROR...");
    response.end();
}

function onRequest(request, response) {
    console.log("url :" + request.url);
    
    if(request.method == 'GET' && request.url == '/') {
        response.writeHead(200, {"content-type" : "text/html"});
        fs.createReadStream("./index.html").pipe(response);
    } 
    
    else if(request.url == '/submit' && request.method == 'POST') {
        console.log('POST function executed!');
        let data = '';
        request.on('data', chunk => {
            data += chunk.toString();
        });

        request.on('end', () => {
            console.log('Received data: ', data);
            response.writeHead(200, {'Content-Type' : 'text/plain'});
            response.end('Data received Succesfully!');
        });
    }
    
    else {
        notFound(response);
    }
    
}

const server = http.createServer((request, response) => {
    onRequest(request, response);
});


server.listen(8080);
console.log("Server Start");