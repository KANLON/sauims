(function() {
  'use strict';
  var $ = window.jQuery;

  /**
   * 点击某个消息后的id
   */
  var CHECKID;

  var Row = function(i, id) { //行构造函数
    var $li = $('<li></li>', {
      'class': 'message-item pointer',
      'id': '' + id
    });
    var $div0 = $('<div></div>', {
      'class': 'cell-unread'
    });

    var $div1 = $('<div></div>', {
      'class': 'message-item-unread unread',
      'data-message-item': 'unread'
    });
    $div0.append($div1);

    var $div2 = $('<div></div>', {
      'class': 'cell-message-info'
    });
    var $div3 = $('<div></div>', {
      'class': 'message-item-title'
    });
    var $span = $('<span></span>', {
      'data-message-item': 'title',
      'class': 'messageTitle' + i
    });
    $div3.append($span);
    var $div4 = $('<div></div>', {
      'class': 'message-item-option'
    });
    var $input = $('<input></input>', {
      'type': 'checkbox',
      'data-message-item': 'option'
    });
    $div4.append($input);
    var $div5 = $('<div></div>', {
      'class': 'message-item-sender'
    });
    var $span1 = $('<span></span>', {
      'ata-message-item': 'sender',
      'class': 'messageSender' + i
    });
    $div5.append($span1);
    var $div6 = $('<div></div>', {
      'class': 'message-item-time'
    });
    var $span2 = $('<span></span>', {
      'data-message-item': 'time',
      'class': 'messageTime' + i
    });
    $div6.append($span2);

    $div2.append($div3);
    $div2.append($div4);
    $div2.append($div5);
    $div2.append($div6);
    $li.append($div0);
    $li.append($div2);

    return $li;

  };

  var json = {}; //全局



  function getNewsData() { //从服务器获取数据

    $.ajax({
        url: '/msg',
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



  var load = function() { //加载

    var messageId; //没错 这就是真正的数据 // FIXME: 变量未使用
    var messageTitle;
    var releaseTime;
    var releaseName;
    var readFlag;

    for (var i = 0; i < json.data.length; i++) {
      messageId = json.data[i].messageId;
      messageTitle = json.data[i].messageTitle;
      releaseName = json.data[i].releaseName;
      //格式化时间格式
      Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日";
      };
      releaseTime = new Date(json.data[i].releaseTime).toLocaleString();

      console.log(messageId);
      /*获取数据后操作dom*/
      $('#News').append(Row(i, messageId));
      if(messageTitle.length>10){messageTitle=messageTitle.substring(0,10)+'...';}
      $('.messageTitle' + i).text(messageTitle);
      $('.messageSender' + i).text("by " + releaseName);
      $('.messageTime' + i).text(releaseTime);
    }
  };

  function addNewsFirst(jsonx) {
    var messageId = jsonx.data[0].messageId;
    $.ajax({
        url: '/msg/' + messageId + '',
        type: 'get',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'json',
        //data: json.data[i],
      })
      .done(function(json1) { //返回来的只是特定id的json 所以下标是0
        CHECKID=messageId;
        Date.prototype.toLocaleString = function() {
          return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日 " + this.getHours() + ":" + this.getMinutes();
        };
        news(json1.data.messageTitle, new Date(json1.data.releaseTime).toLocaleString(), json1.data.messageTitle, json1.data.messageContent);
      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });
  }



  function addNewsClick(jsonx) {
    for (var i = 0; i < jsonx.data.length; i++) {
      $('#' + jsonx.data[i].messageId).click(function() {
        $.ajax({
            url: '/msg/' + this.id + '',
            type: 'get',
            headers: {
              'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            dataType: 'json',
            //data: json.data[i],
          })
          .done(function(json1) { //返回来的只是特定id的json 所以下标是0
            CHECKID=json1.data.messageId;
            Date.prototype.toLocaleString = function() {
              return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日 " + this.getHours() + ":" + this.getMinutes();
            };
            news(json1.data.messageTitle, new Date(json1.data.releaseTime).toLocaleString(), json1.data.messageTitle, json1.data.messageContent);
          })
          .fail(function() {
            console.log('error');
          })
          .always(function() {
            console.log('complete');
          });

      });
    }
  }


  function news(a, b, c, d) { //点击新闻显示
    $("[data-message-detail='sender']").text(a);
    $("[data-message-detail='time']").text(b);
    $("[data-message-detail='title']").text(c);
    $('.para-indent').text(d);
  }

  function delMessage() {
      var deleteIds="";
    var delJson = {
      "_method": "delete",
      "deleteMsgIds": ""

    };
    var arry = $('#News input');
    for (var i = 0; i < arry.length; i++) { //获取所需删除的新闻 然后把信息写进json里 发出去给服务器
      if (arry[i].checked) {
        var x = i;
        //var str='{ \'messageId\':'+$('#News').find('li').eq(x).attr('id')+'}';
        delJson.deleteMsgIds += $('#News').find('li').eq(x).attr('id') + ',';
          deleteIds += $('#News').find('li').eq(x).attr('id') + ',';
      }

    }

      var settings = {
          "async": true,
          "crossDomain": true,
          "url": "/msg",
          "method": "DELETE",
          "headers": {
              "Content-Type": "application/json"
          },
          "processData": false,
          "data": "{\n\t\"deleteMsgIds\":\"deleteIds\"\t\n}"
      }

      $.ajax(settings).done(function (response) {
          if(response.code!=0){
              alert(response.msg);
          }else{
              console.log(response);
              refresh();
          }
      });
    /*$.ajax({
        url: '/msg',
        type: "post",
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


          var arry = $('#News input');
          for (var i = 0; i < arry.length; i++) { //获取所需删除的新闻 然后把信息写进json里 发出去给服务器
            if (arry[i].checked) {
              var x = i;
              $('#News').find('li').eq(x).hide(); //成功后删除所选div
            }

          }

        }
      })
      .fail(function() {
        alert('error');
      })
      .always(function() {
        console.log('complete');
      });*/

  }

  /**
   * 详细详细内的删除
   * @return 是否成功
   */
  function delSingle() {
    var delJson = {
      '_method': 'delete',
      'deleteMsgIds': ''
    };

    delJson.deleteMsgIds = CHECKID;
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
        } else if (Json.code === 0) {
          $('#' + delJson.deleteMsgIds).hide(); //成功后删除所选div
          refresh();
        }

      })
      .fail(function() {
        alert('error');
      })
      .always(function() {
        console.log('complete');
      });

  }



  function refresh() { //刷新按钮
    $('#News').children('li').remove();
    getNewsData(); // FIXME: load没定义！！！

  }

  function getSearchData() {
    $('#News').children('li').remove();
    $.ajax({
        url: '/msg/search?' + 'findContent='+ $('.search-bar').val() + '&offset=1&limit=10000000',
        type: 'get',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'json',
        data:null
      })
      .done(function(searchData) {
        search(searchData);
      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });


  }



  function search(json) {
    var messageId; // FIXME: 变量未使用
    var messageTitle;
    var releaseTime;
    var releaseName;
    var readFlag;

    if (json.code === 0) {

      for (var i = 0; i < json.data.length; i++) {

        messageId = json.data[i].messageId;
        messageTitle = json.data[i].messageContent;
        releaseName = json.data[i].releaseName;
        //格式化时间格式
        Date.prototype.toLocaleString = function() {
          return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日";
        };
        releaseTime = new Date(json.data[i].releaseTime).toLocaleString();

        console.log(messageId);
        /*获取数据后操作dom*/
        $('#News').append(Row(i, messageId));
        $('.messageTitle' + i).text(messageTitle);
        $('.messageSender' + i).text("by " + releaseName);
        $('.messageTime' + i).text(releaseTime);
      }
    }

    addNewsClick(json);


  }



  function addHandler(id, action, func) { //事件监听器
    var domID = document.querySelector(`#${id}`);
    domID.addEventListener(action, function(event) {
      event.preventDefault();
      func(domID.value);
    });
  }

  function init() {
    addHandler('deleteButton', 'click', delMessage);
    addHandler('refresh', 'click', refresh);
    addHandler('search', 'click', getSearchData);
    addHandler('singleDel', 'click', delSingle);
  }


  init();


}());