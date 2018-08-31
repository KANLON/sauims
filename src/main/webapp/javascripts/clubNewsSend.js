(function() {
  'use strict';
  var $ = window.jQuery;
  var json = {};

  function row(i, id) {
    var $div0 = $('<div></div>', {
      'class': 'm',
      'id': '' + id
    });
    var $div1 = $('<div></div>', {
      'class': 'movebar'

    });
    var $div2 = $('<div></div>', {
      'class': 'MTITLE',
      'id': 'MTITLE' + i
    });
    var $div3 = $('<div></div>', {
      'class': 'WRITER',
      'id': 'WRITER' + i
    });
    var $div4 = $('<div></div>', {
      'class': 'MTIME',
      'id': 'MTIME' + i
    });
    var $input = $('<input></input>', {
      'class': 'MCHECK',
      'type': 'checkbox',
      'id': 'MCHECK' + i
    });

    $div0.append($div1);
    $div0.append($div2);
    $div0.append($div3);
    $div0.append($div4);
    $div0.append($input);

    return $div0;

  }

  function getNewsData() { //从服务器获取数据

    $.ajax({
        url: '/club/msg/old?offset=1&limit=10',
        type: 'get',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'json',
      })
      .done(function(Json) {
        console.log('success'); //操作
        if (Json.code != 0) {
          alert(data.msg); // FIXME: data为定义！！！
        }
        json = Json;
        load();
      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });

  }
  getNewsData();

  // var Json = {};

  function load() { //加载


    var auditMsgId; //没错 这就是真正的数据
    var auditTitle;
    var registerTime;
    var auditState; // FIXME: 变量未使用

    for (var i = 0; i < json.data.length; i++) { //i的长度是json的 data的长度
      auditMsgId = json.data[i].messageId; //没错 这就是真正的数据
      auditTitle = json.data[i].messageTitle;
      var unixTimestamp = new Date(json.data[i].sendTime);
      registerTime = unixTimestamp.toLocaleString();

      auditState = json.data[i].messageType;

      var jmz = {};
      jmz.GetLength = function(str) {
        return str.replace(/[\u0391-\uFFE5]/g, "aa").length;
      }
      if (jmz.GetLength(auditTitle) < 19) {
        $('#MTITLE' + i).text(auditTitle);
      } else {
        $('#MTITLE' + i).text("" + auditTitle.substr(0, 10) + "....");
      }
      
      /*获取数据后操作dom*/
      $('#middleSide').append(row(i, json.data[i].auditMsgId));
      $('#MTITLE' + i).text(auditTitle);
      $('#WRITER' + i).text(auditMsgId);
      $('#MTIME' + i).text(registerTime);


    }
  }



  function refresh() { //刷新按钮
    $('#middleSide').children('div').remove();
    load();

  }



  var checkID;

  function addNewsClick(json) {
    for (var i = 0; i < json.data.length; i++) {
      $('#' + json.data[i].messageId).click(function() {
        $.ajax({
            url: '/club/msg/old/' + this.id + '',
            type: 'get',
            headers: {
              'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            dataType: 'json',

          })
          .done(function(json) {
            checkID = json.data[i].messageId;
            news(json.data[0].messageTitle, '', json.data[0].sendTime, json.data[0].messageContent);


          })
          .fail(function() {
            // json
            console.log('error');
          })
          .always(function() {
            console.log('complete');
          });

      });
    }
  }
  addNewsClick(json);

  function news(a, b, c, d) {
    $('#rightHeadTitle').text(a);
    $('#bossName').text(a);
    var unixTimestamp = new Date(c);
    var submitTime = unixTimestamp.toLocaleString();
    $('#rightHeadTime').text(submitTime);

    $('#zhuceneirong').text(d);

  }


  var searchData1 = {};

  function getSearchData() {
    $.ajax({
        url: '/club/msg/old/search?findContent=' + $('.search-bar').val() + '&offset=1&limit=1000000',
        type: 'get',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'json',
        data: null

      })
      .done(function(searchData) {
        searchData1 = searchData;
        search();
      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });


  }

  function search() {



    $('.middleSide').children('div').remove();
    var auditMsgId; //没错 这就是真正的数据
    var auditTitle;
    var registerTime;
    // var auditState; // FIXME: 变量未使用
    if (searchData.code === 0) {
      for (var i = 0; i < searchData.data.length; i++) { //i的长度是json的 data的长度
        auditMsgId = searchData.data[i].messageId; //没错 这就是真正的数据
        auditTitle = searchData.data[i].messageTitle;
        var unixTimestamp = new Date(searchData.data[i].sendTime);
        registerTime = unixTimestamp.toLocaleString();

        auditState = searchData.data[i].messageType;


        /*获取数据后操作dom*/
        $('#middleSide').append(row(i, searchData.data[i].auditMsgId));
        $('#MTITLE' + i).text(auditMsgId);
        $('#WRITER' + i).text(auditTitle);
        $('#MTIME' + i).text(registerTime);
      }
    }

    addNewsClick(searchData1);

  }


  function sendPerson() {
    $.ajax({
        url: '/club/msg/new/person',
        type: 'POST',
        dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
        data: {
          "messageTitle": $("#biaoti").val(),
          "messageContent": $("#neirong").val(),
          "sendTime": 1523266240332,
          "publishedObject": $("#shoujianren").val()
        },
      })
      .done(function() {
        alert("发送成功");
        close();
      })
      .fail(function() {
        console.log("error");
      })
      .always(function() {
        console.log("complete");
      });


  }

  function sendGroup() {
    $.ajax({
        url: '/club/msg/new/group',
        type: 'POST',
        dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
        data: {
          "messageTitle": $("#biaoti").val(),
          "messageContent": $("#neirong").val(),
          "sendTime": 1523266240332,
          "publishedObject ": ""
        },
      })
      .done(function() {
        alert("发送成功");
        close();
      })
      .fail(function() {
        console.log("error");
      })
      .always(function() {
        console.log("complete");
      });

  }

  function sendAll() {
    $.ajax({
        url: '/club/msg/new/all',
        type: 'POST',
        dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
        data: {
          "messageTitle": $("#biaoti").val(),
          "messageContent": $("#neirong").val(),
          "sendTime": 1523266240332,
          "publishedObject": ""
        },
      })
      .done(function() {
        alert("发送成功");
        close();
      })
      .fail(function() {
        console.log("error");
      })
      .always(function() {
        console.log("complete");
      });


  }


  function contact() {
    $.ajax({
        url: 'club/members?messageType=2',
        type: 'GET',
        dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',

      })
      .done(function() {
        console.log("success");
      })
      .fail(function() {
        console.log("error");
      })
      .always(function() {
        console.log("complete");
      });


  }

  function edit() {
    document.getElementById("RightHead").style.display = "none";
    document.getElementById("rightSide").style.display = "none";
    document.getElementById("rightEditTitle").style.display = "block";
    document.getElementById("rightSideEdit").style.display = "block";


  }

  function close() {
    document.getElementById("RightHead").style.display = "block";
    document.getElementById("rightSide").style.display = "block";
    document.getElementById("rightEditTitle").style.display = "none";
    document.getElementById("rightSideEdit").style.display = "none";


  }



  function addHandler(id, action, func) {
    var domID = document.querySelector(`#${id}`);
    domID.addEventListener(action, function(event) {
      event.preventDefault();
      func(domID.value);

    });
  }

  function addHandler(id, action, func, x) {
    var domID = document.querySelector(`#${id}`);
    domID.addEventListener(action, function(event) {
      event.preventDefault();
      func(x);

    });
  }



  function init() {
    addHandler('add', 'click', edit); //添加按钮这样写方便管理sendPicture0
    addHandler('sendPic3', 'click', close);
    addHandler('sendPicture0', 'click', sendPerson);
    addHandler('sendPicture1', 'click', sendGroup);
    addHandler('sendPicture2', 'click', sendAll);
    addHandler('peoplePic', 'click', contact);
    addHandler('search', 'click', getSearchData);

  }
  init();

}());