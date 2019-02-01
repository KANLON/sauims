const express = require('express');
const path = require('path');
const request = require('request');
const app = express();

// 要代理的服务器地址 
let serverUrl='http://localhost:80';
// ./sauims 是本地静态资源文件，相当于服务器的根目录
app.use(express.static(path.join(__dirname, './sauims')));
app.use('/', function(req, res) {
  let url = serverUrl + req.url;
  req.pipe(request(url)).pipe(res);
});
app.listen(3000, function () {
  console.log('server is running at port 3000');
});
