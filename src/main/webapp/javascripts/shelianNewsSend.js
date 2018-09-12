(function() {
  'use strict';
  var $ = window.jQuery;
  var json = {}; //全局

  //存放接收者的map
  var receiversAndID = {};

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
    /*
	 *这个暂时没有用，吴非没有该设计
    var $div3 = $('<div></div>', {
      'class': 'WRITER',
      'id': 'WRITER' + i
    });
	*/
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
    //$div0.append($div3);
    $div0.append($div4);
    $div0.append($input);

    return $div0;

  }

  function getNewsData() { //从服务器获取数据
    $('.middleSide').children('div').remove();
    console.log('执行次数1');
    $.ajax({
        url: '/sau/msg/old',
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
        addNewsClick(json);
        addNewsFirst(json);
      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });

  }

  getNewsData();

  function load() { //加载
    var auditMsgId; //没错 这就是真正的数据
    var auditTitle;
    var auditState; // FIXME: 变量未使用

    for (var i = 0; i < json.data.length; i++) { //i的长度是json的 data的长度
      auditMsgId = json.data[i].messageId; //没错 这就是真正的数据
      auditTitle = json.data[i].messageTitle;
      var unixTimestamp = new Date(json.data[i].sendTime);
      //将时间规范化
      Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "/" + (this.getMonth() + 1) + "/" + this.getDate();
      };

      auditState = json.data[i].messageType;



      /*获取数据后操作dom*/
      $('.middleSide').append(row(i, json.data[i].messageId));
      var jmz = {};
      jmz.GetLength = function(str) {
        return str.replace(/[\u0391-\uFFE5]/g, "aa").length;
      }
      if (jmz.GetLength(auditTitle) < 19) {
        $('#MTITLE' + i).text(auditTitle);
         var lengthA =  jmz.GetLength(auditTitle);
      } else {
        $('#MTITLE' + i).text("" + auditTitle.substr(0, 10) + "....");
      }
      $('#WRITER' + i).text(auditMsgId);
      $('#MTIME' + i).text(unixTimestamp.toLocaleString());

    }
  }



  function refresh() { //刷新按钮
    $('.middleSide').children('div').remove();
    getNewsData(json);
  }

  var checkID;

  function addNewsClick(json) {

    for (var i = 0; i < json.data.length; i++) {
      $('#' + json.data[i].messageId).click(function() {
        close();
        $.ajax({
            url: '/sau/msg/old/' + this.id + '',
            type: 'get',
            headers: {
              'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            dataType: 'json',

          })
          .done(function(json) {
            checkID = json.data.messageId;
            news(json.data.messageTitle, '', json.data.sendTime, json.data.messageContent);
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


  function news(a, b, c, d) {
    $('#rightHeadTitle').text(a);
    $('#bossName').text(a);
    var unixTimestamp = new Date(c);
    //将时间规范化
    Date.prototype.toLocaleString = function() {
      return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日 " + this.getHours() + ":" + this.getMinutes();
    };
    var submitTime = unixTimestamp.toLocaleString();
    $('#rightHeadTime').text(submitTime);
    $('#zhuceneirong').text(d);
  }


  function getSearchData() {
    $('#middleSide').children('div').remove();
    $.ajax({
        url: '/sau/msg/old/search/' + '?findContent=' + $('.searchBar').val() + '&offset=1&limit=100000',
        type: 'get',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'json',
        data: null
      })
      .done(function(searchData) {
        if (searchData.code != 0) {
          alert(search.msg);
        } else {
          search(searchData);
        }
      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });


  }


  function search(searchData) {
    var auditMsgId; //没错 这就是真正的数据
    var auditTitle;
    var registerTime;
    var role;
    var auditState;
    var registerName;

    for (var i = 0; i < searchData.data.length; i++) { //i的长度是json的 data的长度
      auditMsgId = searchData.data[i].messageId; //没错 这就是真正的数据
      auditTitle = searchData.data[i].messageTitle;
      registerName = searchData.data[i].sendTime;

      //将时间规范化
      Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "/" + (this.getMonth() + 1) + "/" + this.getDate() + "";
      };
      var unixTimestamp = new Date(searchData.data[i].sendTime);
      registerTime = unixTimestamp.toLocaleString();
      role = searchData.data[i].role;
      auditState = searchData.data[i].auditState;
      /*获取数据后操作dom*/
      $('#middleSide').append(row(i, searchData.data[i].messageId));
      $('#MTITLE' + i).text(auditTitle);
      $('#WRITER' + i).text(registerName);
      $('#MTIME' + i).text(registerTime);

    }
    addNewsClick(searchData);

  }


  function sendGroup() {
    $("#shoujianren").val("");
    $("#shoujianren").val("@社团内");
  }

  function sendAll() {
    $("#shoujianren").val("");
    $("#shoujianren").val("@所有人");
  }

  $(".showContact").slideUp('slow/400/fast', function() {});

  $("#peoplePic").click(function(event) {
    $(".showContact").slideToggle();
  });

  function sendMsg() {
    var sendMsgURL = '';
    var receiversArr = $('#shoujianren').val().split('@');
    var publishedObject = '';
    //发送类型，如果是发送给所有人，则等于0，如果发送给社团内，则等于1，如果自定义则等于2
    var sendType = 2;
    for (var i = 0; i < receiversArr.length; i++) {
      var name = receiversArr[i];
      if (receiversAndID.hasOwnProperty(name)) {
        publishedObject += "" + receiversAndID[name] + ',';
      }
      //判断消息是否是全体消息还是社团内消息
      if (receiversArr[i] === '所有人') {
        sendType = 0;
        publishedObject = "";
        sendMsgURL = '/sau/msg/new/all';
      } else if (receiversArr[i] === '社团内') {
        sendType = 1;
        publishedObject = "";
        sendMsgURL = '/sau/msg/new/group';
      }
      //判断是不是自定义发送类型的
      if (sendType === 2 && publishedObject != '') {
        sendMsgURL = '/sau/clubs?messageType=2';
      }
    }
    var sendTime = new Date().getTime();

    $.ajax({
        url: sendMsgURL,
        type: 'POST',
        dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
        data: {
          "messageTitle": $("#biaoti").val(),
          "messageContent": $("#neirong").val(),
          "sendTime": sendTime,
          "publishedObject": publishedObject
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

    $(" .saveContact").val("");
    $("#shoujianren").val("");

    $.ajax({
        url: '/sau/club',
        type: 'get',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'json',

      })
      .done(function(json) {
        $(".selectContact").children().remove();
        var json1 = json;
        var x = [];


        for (var i = 0; i < json.data.length; i++) {
          $(".selectContact").append("<li id=" + json.data[i].clubId + ">" + "@" + json.data[i].clubName + "</li>");
          x[json1.data[i].clubId] = json.data[i].clubName;
          // 参数：prop = 属性，val = 值
          var createJson = function(prop, val) {
            // 如果 val 被忽略  
            if (typeof val === "undefined") {
              // 删除属性  
              delete receiversAndID[prop];
            } else {
              // 添加 或 修改  
              receiversAndID[prop] = val;
            }
          }

          createJson(json.data[i].clubName, json1.data[i].clubId);

        }

        for (var i = 0; i < json.data.length; i++) {
          $("#" + json.data[i].clubId).click(function(event) {

            $("#shoujianren").val($("#shoujianren").val() + "@" + x[this.id]);
            $(".saveContact").val($(".saveContact").val() + "," + this.id);
          });
        }


      })
      .fail(function() {
        console.log("error");
      })
      .always(function() {
        console.log("complete");
      });


  }

  function addNewsFirst(jsonx) {
    $.ajax({
        url: '/sau/msg/old/' + jsonx.data[0].messageId + '',
        type: 'get',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'json',
      })
      .done(function(JSON1) {
        news(JSON1.data.messageTitle, '', JSON1.data.sendTime, JSON1.data.messageContent);
      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });
  }



  function delMessage() {
    var delJson = {
      '_method': 'delete',
      'deleteMsgIds': ''

    };
    var arry = $('.MCHECK');
    for (var i = 0; i < arry.length; i++) { //获取所需删除的新闻 然后把信息写进json里 发出去给服务器
      if (arry[i].checked) {
        var x = i;
        //var str='{ \'messageId\':'+$('#News').find('li').eq(x).attr('id')+'}';
        delJson.deleteMsgIds += $('.middleSide').find('div').eq(x).attr('id') + ',';
        //delJson.deleteMsgIds.unshift(str);
      }

    }

    $.ajax({
        url: '/msg',
        type: 'post',
        headers: {
          'Content-type': 'application/json;charset=UTF-8'
        },
        dataType: 'json',
        data: delJson,
      })
      .done(function(Json) {
        if (Json.code != 0) {
          alert(Json.msg);
        }
        if (Json.code === 0) {
          var arry = $('.MCHECK');
          for (var i = 0; i < arry.length; i++) { //获取所需删除的新闻 然后把信息写进json里 发出去给服务器
            if (arry[i].checked) {
              var x = i;

              $('.m').eq(x).hide(); //成功后删除所选div

            }

          }

        }
      })
      .fail(function() {
        alert('error');
      })
      .always(function() {
        console.log('complete');
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



  function init() {
    addHandler('add', 'click', edit); //添加按钮这样写方便管理sendPicture0
    addHandler('sendPic3', 'click', close);
    addHandler('sendAll', 'click', sendAll);
    addHandler('clubSend', 'click', sendGroup);
    addHandler('selfSend', 'click', contact);
    addHandler('sendPic2', 'click', sendMsg);
    addHandler('refresh', 'click', refresh);
    addHandler('search', 'click', getSearchData);
    addHandler('delete', 'click', delMessage);
  }
  init();


}());