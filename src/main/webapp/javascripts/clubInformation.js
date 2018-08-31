(function() {
  'use strict';
  var $ = window.jQuery;
  var echarts = window.echarts;
  var json = {};

  function row(i, id) {
    var $div0 = $('<div></div>', {
      'class': 'm',
      'id': '' + id
    });
    var $img = $('<img></img>', {
      'class': 'MHEAD',
      'src': '/resource/logo/'
    });
    var $div1 = $('<div></div>', {
      'class': 'WRITER',
      'id': 'WRITER' + i
    });
    var $div2 = $('<div></div>', {
      'class': 'NUM',
      'id': 'NUM' + i
    });
    var $a = $('<a></a>', {
      'href': ''
    });
    var $img1 = $('<img></img>', {
      'class': 'LIKE',
      'src': './images/heart.png'
    });
    $a.append($img1);
    $div0.append($img);
    $div0.append($div1);
    $div0.append($div2);
    $div0.append($a);

    return $div0;

  }

  function getNewsData() { //从服务器获取数据

    return $.ajax({
        url: '/sau/club',
        type: 'get',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'json',
      })
      .done(function(Json) {
        console.log('success'); //操作
        console.log(Json);
        if (Json.code != 0) {
          alert(data.msg); // FIXME: data为定义！！！
        }
        json = Json;
        load();
        addNewsClick(json);
        loadFirstOrgMsg(json);
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
    var orgId; //没错 这就是真正的数据 // FIXME: 变量未使用
    var orgName;
    var members; // FIXME: 变量未使用
    var likeClick;

    for (var i = 0; i < json.data.length; i++) { //i的长度是json的 data的长度
      orgId = json.data[i].orgId; //没错 这就是真正的数据
      orgName = json.data[i].orgName;
      members = json.data[i].members;
      likeClick = json.data[i].likeClick;

      var jmz = {};
      jmz.GetLength = function(str) {
        return str.replace(/[\u0391-\uFFE5]/g, "aa").length;
      }
      if (jmz.GetLength(orgName) < 19) {
        $('#WRITER' + i).text(orgName);
      } else {
        $('#WRITER' + i).text("" + orgName.substr(0, 10) + "....");
      }
      

      /*获取数据后操作dom*/
      $('.middleSide').append(row(i, json.data[i].orgId));
      $('.MHEAD').attr("src", "/resource/logo/" + json.data[i].logo);
      $('#WRITER' + i).text(orgName);
      $('#NUM' + i).text(likeClick);
    }
  }

  /**
   *加载第一个社团信息，用于刚刚进入到页面时，加载第一个社团信息
   *
   */
  function loadFirstOrgMsg(json) {
    $.ajax({
        url: '/sau/club/' + json.data[0].orgId + '',
        type: 'get',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'json',

      })
      .done(function(JSON1) {
        //将时间规范化
        Date.prototype.toLocaleString = function() {
          return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日";
        };
        console.log(JSON1);
        news(JSON1.data.orgName, JSON1.data.headIntroduce, new Date(JSON1.data.foundTime).toLocaleString(), JSON1.data.adminName, JSON1.data.email, JSON1.data.phone, JSON1.data.description);
        //绘图
        drawing(JSON1.data.manNum, JSON1.data.womanNum, JSON1.data.firstGradeNum, JSON1.data.secondGradeNum, JSON1.data.threeGradeNum, JSON1.data.fourGradeNum);
      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });
  }


  /*
   *点击某个社团时，加载该社团信息在最右边的信息展示区
   *
   */
  function addNewsClick(json) {
    for (var i = 0; i < json.data.length; i++) {
      $('#' + json.data[i].orgId).click(function() {
        $.ajax({
            url: '/sau/club/' + this.id + '',
            type: 'get',
            headers: {
              'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
            },
            dataType: 'json',

          })
          .done(function(JSON1) {
            //将时间规范化
            Date.prototype.toLocaleString = function() {
              return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日";
            };
            console.log(JSON1);
            news(JSON1.data.orgName, JSON1.data.headIntroduce, new Date(JSON1.data.foundTime).toLocaleString(), JSON1.data.adminName, JSON1.data.email, JSON1.data.phone, JSON1.data.description);
            //绘图
            drawing(JSON1.data.manNum, JSON1.data.womanNum, JSON1.data.firstGradeNum, JSON1.data.secondGradeNum, JSON1.data.threeGradeNum, JSON1.data.fourGradeNum);
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


  function news(a, b, c, d, e, f, g) {
    $('.rightHeadTitle').text(a);
    $('.rightHeadIntroduce').text(b);
    $('.rightHeadTime').text(c + " 成立");

    $('#bossName').text(d);
    $('#mailboxName').text(e);
    $('#telNum').text(f);
    $('#Introduce').text(g);

  }


  function refresh() { //刷新按钮
    $('.middleSide').children('div').remove();
    getNewsData();
  }

  function getSearchData() { // FIXME: 变量未使用
    $('.middleSide').children('div').remove();
    $.ajax({
        url: '/sau/club/search'+'?findContent=' + $('.searchBar').val() + '&offset=1&limit=1000000',
        type: 'get',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'json',
        data: null
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
    var orgId; //没错 这就是真正的数据 // FIXME: 变量未使用
    var orgName;
    var members; // FIXME: 变量未使用
    var likeClick;

    for (var i = 0; i < json.data.length; i++) { //i的长度是json的 data的长度
      orgId = json.data[i].orgId; //没错 这就是真正的数据
      orgName = json.data[i].orgName;
      members = json.data[i].members;
      likeClick = json.data[i].likeClick;

      /*获取数据后操作dom*/
      $('.middleSide').append(row(i, json.data[i].orgId));
      $('.MHEAD').attr("src", "/resource/logo/" + json.data[i].logo);
      $('#WRITER' + i).text(orgName);
      $('#NUM' + i).text(likeClick);
    }
    addNewsClick(json);

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
    addHandler('search', 'click', getSearchData);

  }

  init();



  /*
   *绘图的函数，
   *绘制男女生和社团内大一，大二，大三，大四的比例图
   *   
   */
  function drawing(manNum, womanNum, firstGradeNum, secondGradeNum, threeGradeNum, fourGradeNum) {

    //绘图

    //---------------------------------------------------------------------
    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('Boypic1'));

    // 指定图表的配置项和数据
    var option = {
      color: ['#37a2fe', '#8dcaea', '#327aa7'],
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        x: 'left',
        data: ['男', '女']
      },
      series: [{
        name: '男女比例',
        type: 'pie',
        radius: ['50%', '70%'],
        avoidLabelOverlap: false,
        label: {
          normal: {
            show: false,
            position: 'center'
          },
          emphasis: {
            show: true,
            textStyle: {
              fontSize: '30',
              fontWeight: 'bold'
            }
          }
        },
        labelLine: {
          normal: {
            show: false
          }
        },
        data: [{
            value: manNum,
            name: '男'
          }, {
            value: womanNum,
            name: '女'
          },

        ]
      }]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);


    //绘图二
    //----------------------------------------------------------------

    // 基于准备好的dom，初始化echarts实例
    var myChart1 = echarts.init(document.getElementById('Boypic2'));

    // 指定图表的配置项和数据
    var option1 = {
      color: ['#37a2fe', '#8dcaea', '#327aa7', '#000FFF'],
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b}: {c} ({d}%)'
      },
      legend: {
        orient: 'vertical',
        x: 'left',
        data: ['大一', '大二', '大三', '大四']
      },
      series: [{
        name: '年级比例',
        type: 'pie',
        radius: ['50%', '70%'],
        avoidLabelOverlap: false,
        label: {
          normal: {
            show: false,
            position: 'center'
          },
          emphasis: {
            show: true,
            textStyle: {
              fontSize: '30',
              fontWeight: 'bold'
            }
          }
        },
        labelLine: {
          normal: {
            show: false
          }
        },
        data: [{
          value: firstGradeNum,
          name: '大一'
        }, {
          value: secondGradeNum,
          name: '大二'
        }, {
          value: threeGradeNum,
          name: '大三'
        }, {
          value: fourGradeNum,
          name: '大四'
        }, ]
      }]
    };


    // 使用刚指定的配置项和数据显示图表。
    myChart1.setOption(option1);

  }

}());