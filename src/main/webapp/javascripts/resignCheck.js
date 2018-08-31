(function() {
  'use strict';
  var $ = window.jQuery;
  var json = {}; //全局
  /**
   * 详细审核消息的id(用于同意或驳回注册消息)
   */
  var checkID = 1;
  /**
   * 是审核社员（0）还是审核社团（1）(用于同意或驳回注册消息)
   */
  var ROLE = 1;

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
        url: '/sau/audit/reg',
        type: 'get',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'json',
      })
      .done(function(Json) {
        console.log('success');
        if (Json.code != 0) {
          alert(Json.data.msg);
        }
        json = Json;
        load(json);
        addNewsClick(json);
        loadFirstAuditMsg(json);
      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });

  }
  getNewsData();


  function load(json) { //加载
    var auditMsgId; //没错 这就是真正的数据
    var auditTitle;
    var registerTime;
    var role;
    var auditState;
    var registerName;

    for (var i = 0; i < json.data.length; i++) { //i的长度是json的 data的长度
      auditMsgId = json.data[i].auditMsgId; //没错 这就是真正的数据
      auditTitle = json.data[i].auditTitle;
      registerName = json.data[i].registerName;

      //将时间规范化
      Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "/" + (this.getMonth() + 1) + "/" + this.getDate() + "";
      };
      var unixTimestamp = new Date(json.data[i].registerTime);
      registerTime = unixTimestamp.toLocaleString();
      role = json.data[i].role;
      auditState = json.data[i].auditState;

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
      $('#middleSide').append(row(i, json.data[i].auditMsgId + '-' + json.data[i].role));
      $('#MTITLE' + i).text(auditTitle);
      $('#WRITER' + i).text(registerName);
      $('#MTIME' + i).text(registerTime);

    }
  }

  /**
   * 刚刚进来加载第一个审核消息
   */
  function loadFirstAuditMsg(jsonx) {
    var x = jsonx.data[0].auditMsgId + '-' + jsonx.data[0].role;
    var tempId = x.split('-');
    var auditMsgId = tempId[0];
    var role = tempId[1];
    console
    if (role === 0) {
      //点击审核社员注册信息
      $.ajax({
          url: '/sau/audit/join/' + role + '/' + auditMsgId + '',
          type: 'get',
          headers: {
            'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
          },
          dataType: 'json',

        })
        .done(function(json1) {
          console.log(json1);
          checkID = json1.data.auditMsgId;
          ROLE = role;
          news(json1.data.realName, json1.data.clubType, json1.data.registerTime, json1.data.realName, json1.data.email, json1.data.phone, json1.data.description, json1.data.auditMsgId);
        })
        .fail(function() {
          console.log('error');
        })
        .always(function() {
          console.log('complete');
        });
    } else {
      //点击社团注册信息
      $.ajax({
          url: '/sau/audit/join/' + role + '/' + auditMsgId + '',
          type: 'get',
          headers: {
            'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
          },
          dataType: 'json',

        })
        .done(function(json1) {
          console.log(json1);
          checkID = json1.data.auditMsgId;
          ROLE = role;
          news(json1.data.clubName, json1.data.clubType, json1.data.registerTime, json1.data.realName, json1.data.email, json1.data.phone, json1.data.description, json1.data.auditMsgId);
        })
        .fail(function() {
          console.log('error');
        })
        .always(function() {
          console.log('complete');
        });
    }

  }


  function addNewsClick(jsonx) {
    for (var i = 0; i < jsonx.data.length; i++) {
      var x = jsonx.data[i].auditMsgId + '-' + jsonx.data[i].role;
      $('#' + x).click(function(event) {
        var tempId = this.id.split('-');
        var auditMsgId = tempId[0];
        var role = tempId[1];

        if (role === '0') {
          //点击审核社员注册信息
          $.ajax({
              url: '/sau/audit/join/' + role + '/' + auditMsgId + '',
              type: 'get',
              headers: {
                'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
              },
              dataType: 'json',

            })
            .done(function(json1) {
              checkID = auditMsgId;
              ROLE = role;
              //社员注册信息没有附件，除去附件div
              document.getElementById("FUJIAN").style.display = "none";
              news(json1.data.realName, json1.data.clubType, json1.data.registerTime, json1.data.realName, json1.data.email, json1.data.phone, json1.data.description, null);
            })
            .fail(function() {
              console.log('error');
            })
            .always(function() {
              console.log('complete');
            });
        } else {
          //点击社团注册信息
          $.ajax({
              url: '/sau/audit/join/' + role + '/' + auditMsgId + '',
              type: 'get',
              headers: {
                'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
              },
              dataType: 'json',

            })
            .done(function(json1) {
              console.log(json1)
              checkID = auditMsgId;
              ROLE = role;
              //社团审核有附件div，添加
              document.getElementById("FUJIAN").style.display = "block";
              news(json1.data.clubName, json1.data.clubType, json1.data.registerTime, json1.data.realName, json1.data.email, json1.data.phone, json1.data.description, auditMsgId);
            })
            .fail(function() {
              console.log('error');
            })
            .always(function() {
              console.log('complete');
            });
        }
      });

    }
  }

  function news(a, b, c, d, e, f, g, h) {
    $('#rightHeadTitle').text(a);
    $('#clubName1').text(a);
    $('#clubType1').text(b);
    //将时间规范化
    Date.prototype.toLocaleString = function() {
      return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日 " + this.getHours() + ":" + this.getMinutes();
    };
    var unixTimestamp = new Date(c);
    var submitTime = unixTimestamp.toLocaleString();
    $('#rightHeadTime').text(submitTime);

    $('#bossName').text(d);
    $('#mailboxName').text(e);
    $('#telNum').text(f);
    $('#Introduce').text(g);
    $("#fujian").attr({
      href: '/sau/audit/reg/' + h + '/file' + ''
    });

  }


  function refresh() { //刷新按钮
    $('#middleSide').children('div').remove();
    getNewsData();

  }


  function agree() {
    console.log('/sau/audit/reg/' + ROLE + '/' + checkID + '');
    $.ajax({
        url: '/sau/audit/reg/' + ROLE + '/' + checkID + '',
        type: 'post',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'json',
        data: {
          'auditState': 1,
          '_method': 'put'
        },
      })
      .done(function() {
        if (json.code != 0) {
          alert(json.msg);
        } else {
          alert('同意成功');
          //提交之后重置理由区
          document.getElementById("neirong").value = "";
        }
      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });


  }

  function disagree() {
    console.log('/sau/audit/reg/' + ROLE + '/' + checkID + '');
    console.log('不通过理由：' + $('#neirong').val());
    $.ajax({
        url: '/sau/audit/reg/' + ROLE + '/' + checkID + '',
        type: 'post',
        dataType: 'json',
        data: {
          'auditState': 0,
          'auditResult': $('#neirong').val(),
          '_method': 'put'
        },
      })
      .done(function(json) {
        if (json.code != 0) {
          alert(json.msg);
        } else {
          alert('驳回成功');
          //提交之后重置理由区
          document.getElementById("neirong").value = "";
        }
      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });

  }

  function getSearchData() {
    $('#middleSide').children('div').remove();
    $.ajax({
        url: '/sau/audit/reg/search' + '?findContent=' + $('.searchBar').val() + '&offset=1&limit=100000',
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
      auditMsgId = searchData.data[i].auditMsgId; //没错 这就是真正的数据
      auditTitle = searchData.data[i].auditTitle;
      registerName = searchData.data[i].registerName;

      //将时间规范化
      Date.prototype.toLocaleString = function() {
        return this.getFullYear() + "/" + (this.getMonth() + 1) + "/" + this.getDate() + "";
      };
      var unixTimestamp = new Date(searchData.data[i].registerTime);
      registerTime = unixTimestamp.toLocaleString();
      role = searchData.data[i].role;
      auditState = searchData.data[i].auditState;
      /*获取数据后操作dom*/
      $('#middleSide').append(row(i, searchData.data[i].auditMsgId + '-' + searchData.data[i].role));
      $('#MTITLE' + i).text(auditTitle);
      $('#WRITER' + i).text(registerName);
      $('#MTIME' + i).text(registerTime);

    }
    addNewsClick(searchData);

  }

  function addHandler(id, action, func, x) {
    var domID = document.querySelector(`#${id}`);
    domID.addEventListener(action, function(event) {
      event.preventDefault();
      func(x);
    });
  }


  function init() {
    addHandler('refresh', 'click', refresh);
    addHandler('agree', 'click', agree);
    addHandler('disagree', 'click', disagree);
    addHandler('search', 'click', getSearchData);
  }
  init();

}());