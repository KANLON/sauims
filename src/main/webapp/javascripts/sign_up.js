(function(window) {
  'use script';
  var $ = window.jQuery;
 $('.normalForm1').hide();
  function checkEmail() {
    var target = document.getElementById('email');
    var T = document.getElementById('T').style;
    var F = document.getElementById('F').style;
    var word = document.getElementById('mailWrong');
    if (target.value === '') {
      T.display = 'none';
      F.display = 'block';
      F.left = '750px';
      F.top = '198px';
      word.style.display = 'block';
    } else if (!/.+@.\..+$/.test(target.value)) {
      T.display = 'none';
      F.display = 'block';
      F.left = '750px';
      F.top = '198px';
      word.style.display = 'block';
      word.firstChild.data = '请输入正确的邮箱';
    } else {
      F.display = 'none';
      T.display = 'block';
      T.left = '750px';
      T.top = '198px';
      word.style.display = 'none';
    }
  }

  function checkPW() {
    var target = document.getElementById('pw');
    var T = document.getElementById('T1').style;
    var F = document.getElementById('F1').style;
    var word = document.getElementById('PWWrong').style;
    if (target.value === '') {
      T.display = 'none';
      F.display = 'block';
      F.left = '750px';
      F.top = '311px';
      word.display = 'block';
    } else {
      F.display = 'none';
      T.display = 'block';
      T.left = '750px';
      T.top = '311px';
      word.display = 'none';
    }
  }

  function checkSame() {
    var target = document.getElementById('repw');
    var target0 = document.getElementById('pw');
    var T = document.getElementById('T2').style;
    var F = document.getElementById('F2').style;
    var word = document.getElementById('notSame');
    if (target0.value === '') {
      T.display = 'none';
      F.display = 'block';
      F.left = '750px';
      F.top = '365px';

      word.style.display = 'block';
      word.firstChild.data = '请先输入密码';
    } else {
      if (target.value !== target0.value) {
        T.display = 'none';
        F.display = 'block';
        F.left = '750px';
        F.top = '365px';

        word.style.display = 'block';
        word.firstChild.data = '两次输入密码不同';
      } else {
        F.display = 'none';
        T.display = 'block';
        T.left = '750px';
        T.top = '365px';
        word.style.display = 'none';
      }
    }
  }

  function verifySend() {
    var target = document.getElementById('email');

    var T = document.getElementById('T').style;
    var F = document.getElementById('F').style;
    var word = document.getElementById('mailWrong').style;
    if (target.value === '') {
      T.display = 'none';
      F.display = 'block';
      F.left = '750px';
      F.top = '152px';
      word.display = 'block';
    } else {
      $.ajax({
        type: 'get',
        url: '/reg/person/captcha',
        // headers: {'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'},
        dataType: 'json',
        data: {
          'email': target.value
        }
      });
      forbidClick(60);
    }
  }



  function verifySend1() {
    var target = document.getElementById('clubEmail');

    var T = document.getElementById('T').style;
    var F = document.getElementById('F').style;
    var word = document.getElementById('mailWrong').style;
    if (target.value === '') {
      T.display = 'none';
      F.display = 'block';
      F.left = '750px';
      F.top = '152px';
      word.display = 'block';
    } else {
      $.ajax({
        type: 'get',
        url: '/reg/club/captcha',
        // headers: {'Content-type': 'application/x-www-form-urlencoded;charset=UTF-8'},
        dataType: 'json',
        data: {
          'email': target.value
        }
      });
      forbidClick(60);
    }
  }
  function forbidClick(second) {
    var target = document.getElementById('verifySendMember');

    if (second > 0) {
      target.style.cursor = 'not-allowed';
      target.value = '再次发送(' + second + '秒)';
      window.setTimeout(function() {
        forbidClick(second - 1);
      }, 1000);
    } else {
      target.style.cursor = 'pointer';
      target.value = '发送验证码';
    }
  }

  function normalSignUp() {
    var formData = {};
    var formElement = document.querySelector('#normalSignUp');
    $(formElement).serializeArray().forEach(function(item) {
      formData[item.name] = item.value;
    });
    var json = JSON.stringify(formData);
    var url = '/reg/person';
    sendAjax(url, json)
      .done(getResponse);
  }
function clubSignUp() {
 var formData=new FormData();
 var  formElement=document.querySelector('#clubSignUp');
 $(formElement).serializeArray().forEach( function(item) {
   formData[item.name] = item.value;
 });
 var json = JSON.stringify(formData);
  json.file="";

   var url = '/reg/club';
    sendAjax(url, json)
      .done(getResponse);
}
  function sendAjax(url, json) {
    return $.ajax({
      type: 'post',
      data: json,
      url: url,
      headers: {
        'Content-type': 'application/json;charset=UTF-8'
      },
      "processData": false,
      "contentType": false,
      dataType: 'json'
    });
  }

  function getResponse(response) {
    var F = document.getElementById('F2').style;
    var word = document.getElementById('notSame');
    if (response.code === 0) {
      // TODO: 链接未定
    } else {
      F.display = 'block';
      F.left = '750px';
      F.top = '325px';

      word.style.display = 'block';
      word.firstChild.data = response.msg;
    }
  }


 $('.uncheckedSign').click(function(event) {
  $('.uncheckedSign').css('marginTop',10);
   $('.checkedSign').css('marginTop',0);
   $('.normalForm').hide();
    $('.normalForm1').show();
 });


  $('.checkedSign').click(function(event) {
  $('.uncheckedSign').css('marginTop',5);
   $('.checkedSign').css('marginTop',5);
   $('.normalForm1').hide();
    $('.normalForm').show();

 });


  function addHandler(id, action, func) {
    var domID = document.querySelector(`#${id}`);
    domID.addEventListener(action, function(event) {
      event.preventDefault();
      func(domID.value);
    });
  }

  function init() {
    addHandler('email', 'blur', checkEmail);
    addHandler('pwMember', 'blur', checkPW);
    addHandler('pwClub', 'blur', checkPW);
    addHandler('repwMember', 'blur', checkSame);
    addHandler('repwClub', 'blur', checkSame);
    addHandler('verifySendMember', 'click', verifySend);
    addHandler('verifySendClub', 'click', verifySend1);
    addHandler('signButton', 'click', normalSignUp);
    addHandler('signButton1', 'click', clubSignUp);
  }
  init();

}(window));