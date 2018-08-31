(function() {
  'use strict';
  var $ = window.jQuery;
  var echarts = window.echarts;
  var json = {};

  function getNewsData() { //从服务器获取数据

    $.ajax({
        url: '/sau/center/info',
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



  var load = function(){

    var clubName;
    var setTime;
    var bossName;
    var mailbox;
    var Introduce;
  

    clubName = json.data.sauName;
    setTime = json.data.foundTime;
    bossName = json.data.adminName;
    mailbox = json.data.contactEmail;
    Introduce = json.data.description;

    $('#clubName').text(clubName);
    $('#setTime').text(setTime);
    $('#bossName').text(bossName);
    $('#mailbox').text(mailbox);
    $('#Introduce').text(Introduce);
    $('.rightHeadTitle').text(clubName);
    $('#touxiang').attr('src', '' + json.data.logo);

    $('#clubName1').text(clubName);
    $('#setTime1').text(setTime);
    $('#bossName1').text(bossName);
    $('#mailbox1').text(mailbox);
    $('.tx').val(Introduce);


  }



  function showHead() { /*把换头像界面显示出来*/
    var target = document.getElementById('headChange');
    target.style.display = 'block';

  }

  function hiddenHead() { /*隐藏界面*/
    var target = document.getElementById('headChange');
    target.style.display = 'none';
  }

  function showEdit() {
    var target = document.getElementById('rightEdit');
    target.style.display = 'block';

  }

  function hiddenEdit() { /*隐藏界面*/
    var inputjson = {

      'sauName': '' + $('#clubName1').val(),
      'description': '' + $('.tx').val(),
      'adminName': '' + $('#bossName1').val(),
      'foundTime': '2017-10-29',
      '_method': 'put'
    };
    $.ajax({
        url: '/sau/center/info',
        type: 'post',
        headers: {
          'Content-type': 'application/json;charset=UTF-8'
        },
        dataType: 'json',
        data: inputjson,
      })
      .done(function() {

        var target = document.getElementById('rightEdit');
        target.style.display = 'none';
        load();


      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });



  }

  function changeHead() {

    $.ajax({
        url: '/sau/center/info/head',
        type: 'post',
        headers: {
          'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'
        },
        dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
        data: `<input type='file' name='file' id='file'>` + `<input type='hidden' name='_method' value=”put/>`
      })
      .done(function(Json) {
        console.log('success'); //操作
        $('#zhezhao').attr('src', '' + Json.data.logo);
      })
      .fail(function() {
        console.log('error');
      })
      .always(function() {
        console.log('complete');
      });


  }

  function hideShuoming() {
    var target = document.getElementById('shuoming');
    target.style.display = 'none';

  }

  function addHandler(id, action, func) {
    var domID = document.querySelector(`#${id}`);
    domID.addEventListener(action, function(event) {
      event.preventDefault();
      func(domID.value);
    });
  }

  function init() {

    addHandler('headEdit', 'click', showHead);
    addHandler('x', 'click', hiddenHead);
    addHandler('titleEdit', 'click', showEdit);
    addHandler('save', 'click', hiddenEdit);
    addHandler('up', 'click', changeHead);
    addHandler('shuoming', 'click', hideShuoming);


  }
  hiddenHead();

  init();

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
          value: 335,
          name: '男'
        }, {
          value: 310,
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
    color: ['#37a2fe', '#8dcaea', '#327aa7'],
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      x: 'left',
      data: ['大一', '大二', '大三']
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
          value: 335,
          name: '大一'
        }, {
          value: 310,
          name: '大二'
        }, {
          value: 310,
          name: '大三'
        },

      ]
    }]
  };


  // 使用刚指定的配置项和数据显示图表。
  myChart1.setOption(option1);

}());