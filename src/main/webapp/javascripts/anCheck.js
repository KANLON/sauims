(function () {
    'use strict';
    var $ = window.jQuery;
    /**
     * 详细审核消息的id(用于同意或驳回年度注册消息)
     */
    var checkID = 1;
    /**
     * 表示通过的
     * @type {number}
     */
    var pass = 1;
    /**
     * 表示不通过
     * @type {number}
     */
    var unPass = 0

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

    var json = {};

    function getNewsData() { //从服务器获取数据

        $.ajax({
            url: '/sau/audit/ann',
            type: 'get',
            headers: {
                'Content-type': 'application/json;charset=UTF-8'
            },
            dataType: 'json',
        })
            .done(function (Json) {
                if (Json.code != 0) {
                    alert(Json.msg);
                } else {
                    json = Json;
                    load();
                    addNewsClick(Json);
                    loadFirstAnnCheck(json);
                }
            })
            .fail(function () {
                console.log('error');
            })
            .always(function () {
                console.log('complete');
            });

    }

    getNewsData();

    var auditState;

    function load() { //加载
        var registerName; //没错 这就是真正的数据
        var registerTitle;
        var registerTime;
        var auditState; // FIXME: 变量未使用

        for (var i = 0; i < json.data.length; i++) { //i的长度是json的 data的长度
            registerTitle = json.data[i].registerTitle; //没错 这就是真正的数据
            registerName = json.data[i].registerName;
            //将时间规范化
            Date.prototype.toLocaleString = function () {
                return this.getFullYear() + "" /*+ (this.getMonth() + 1) + "月" + this.getDate() + "日 " + this.getHours() + ":" + this.getMinutes()*/;
            };
            var unixTimestamp = new Date(json.data[i].registerTime);
            var registerTime = unixTimestamp.toLocaleString();
            // auditState = json.data[i].auditState;
            /*      var jmz = {};
                  jmz.GetLength = function(str) {
                    return str.replace(/[\u0391-\uFFE5]/g, "aa").length;
                  }
                  if (jmz.GetLength(registerTitle) < 19) {
                    $('#MTITLE' + i).text(registerTitle);
                  } else {
                    $('#MTITLE' + i).text("" + registerTitle.substr(0, 10) + "....");
                  }*/

            /*获取数据后操作dom*/
            $('#middleSide').append(row(i, json.data[i].auditMsgId));
            $('#MTITLE' + i).text(registerTitle);
            $('#WRITER' + i).text(registerName);
            $('#MTIME' + i).text(registerTime);
        }
    }


    function loadFirstAnnCheck(json) {
        var auditMsgId = json.data[0].auditMsgId;
        $.ajax({
            url: '/sau/audit/ann/' + auditMsgId + '',
            type: 'get',
            headers: {
                'Content-type': 'application/json;charset=UTF-8'
            },
            dataType: 'json',

        })
            .done(function (JSON1) {
                checkID = JSON1.data.auditMsgId;
                auditState = JSON1.data.auditState;
                //将时间规范化
                Date.prototype.toLocaleString = function () {
                    return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日 " + this.getHours() + ":" + this.getMinutes();
                };
                var unixTimestamp = new Date(JSON1.data.submitTime);
                var registerTime = unixTimestamp.toLocaleString();
                news(JSON1.data.clubName, '', registerTime, JSON1.data.adminName, JSON1.data.description);
                var fileURL = '/sau/audit/ann/' + checkID + '/file' + '';
                document.getElementById("fujian").href = fileURL;

            })
            .fail(function () {
                console.log('error');
            })
            .always(function () {
                console.log('complete');
            });
    }


    function addNewsClick(json) {
        for (var i = 0; i < json.data.length; i++) {
            $('#' + json.data[i].auditMsgId).click(function () {
                $.ajax({
                    url: '/sau/audit/ann/' + this.id + '',
                    type: 'get',
                    headers: {
                        'Content-type': 'application/json;charset=UTF-8'
                    },
                    dataType: 'json',

                })
                    .done(function (JSON1) {
                        checkID = JSON1.data.auditMsgId;
                        auditState = JSON1.data.auditState;
                        //将时间规范化
                        Date.prototype.toLocaleString = function () {
                            return this.getFullYear() + "年" + (this.getMonth() + 1) + "月" + this.getDate() + "日 " + this.getHours() + ":" + this.getMinutes();
                        };
                        var unixTimestamp = new Date(JSON1.data.submitTime);
                        var registerTime = unixTimestamp.toLocaleString();
                        news(JSON1.data.clubName, '', registerTime, JSON1.data.adminName, JSON1.data.description);
                        var fileURL = '/sau/audit/ann/' + checkID + '/file' + '';
                        document.getElementById("fujian").href = fileURL;
                    })
                    .fail(function () {
                        console.log('error');
                    })
                    .always(function () {
                        console.log('complete');
                    });


            });
        }
    }

    function news(a, b, c, d, e) {
        $('#rightHeadTitle').text(a);
        $('#rightHeadTime').text(c);
        $('#bossName').text(d);
        $('#zhuceneirong').text(e);
    }

    function refresh() { //刷新按钮
        $('#middleSide').children('div').remove();
        getNewsData();

    }

    function agree() {
        console.log('sauims/json/sau/audit/ann/' + checkID + '');
        $.ajax({
            url: '/sau/audit/ann/' + checkID + '',
            type: 'put',
            headers: {
                'Content-type': 'application/json;charset=UTF-8'
            },
            dataType: 'json',
            data: {
                'auditState': '1',
                '_method': 'put'
            },
        })
            .done(function (data) {
                if (data.code != 0) {
                    alert('data.msg');
                } else {
                    alert('通过成功');
                }
                //提交之后重置理由区
                document.getElementById("neirong").value = "";
            })
            .fail(function () {
                console.log('error');
            })
            .always(function () {
                console.log('complete');
            });


    }

    function disagree() {
        console.log('sauims/json/sau/audit/ann/' + checkID + '');
        console.log('不通过理由：' + $('#neirong').val());
        $.ajax({
            url: '/sau/audit/ann/' + checkID + '',
            type: 'put',
            headers: {
                'Content-type': 'application/json;charset=UTF-8'
            },
            dataType: 'json',
            data: {
                'auditState': '0',
                'auditResult': $('#neirong').val(),
                '_method': 'put'
            },
        })
            .done(function (data) {
                if (data.code != 0) {
                    alert('data.msg');
                } else {
                    alert('驳回成功');
                }
                //提交之后重置理由区
                document.getElementById("neirong").value = "";
            })
            .fail(function () {
                console.log('error');
            })
            .always(function () {
                console.log('complete');
            });

    }

    function getSearchData() {
        $('#middleSide').children('div').remove();
        $.ajax({
            url: '/sau/audit/ann/search' + '',
            type: 'get',
            headers: {
                'Content-type': 'application/json;charset=UTF-8'
            },
            dataType: 'json',
            data: null
        })
            .done(function (searchData) {
                search(searchData);
            })
            .fail(function () {
                console.log('error');
            })
            .always(function () {
                console.log('complete');
            });

    }


    function search(json) {
        var registerName; //没错 这就是真正的数据
        var registerTitle;
        var registerTime;
        var auditState; // FIXME: 变量未使用

        for (var i = 0; i < json.data.length; i++) { //i的长度是json的 data的长度
            registerTitle = json.data[i].registerTitle; //没错 这就是真正的数据
            registerName = json.data[i].registerName;
            //将时间规范化
            Date.prototype.toLocaleString = function () {
                return this.getFullYear() + "" /*+ (this.getMonth() + 1) + "月" + this.getDate() + "日 " + this.getHours() + ":" + this.getMinutes()*/;
            };
            var unixTimestamp = new Date(json.data[i].registerTime);
            var registerTime = unixTimestamp.toLocaleString();
            // auditState = json.data[i].auditState;

            /*获取数据后操作dom*/
            $('#middleSide').append(row(i, json.data[i].auditMsgId));
            $('#MTITLE' + i).text(registerTitle);
            $('#WRITER' + i).text(registerName);
            $('#MTIME' + i).text(registerTime);
        }

        addNewsClick(json);

    }


    function file() {
        document.getElementById("file").href = '/sau/audit/ann/' + checkID + '/file' + '';
        $.ajax({
            url: '/sau/audit/ann/' + checkID + '/file' + '',
            type: 'GET',
            headers: {
                'Content-type': 'application/json;charset=UTF-8'
            },
            dataType: 'default: Intelligent Guess (Other values: xml, json, script, or html)',
        })
            .done(function (x) {
                $('#FUPIC1').write(x);
            })
            .fail(function () {
                console.log('error');
            })
            .always(function () {
                console.log('complete');
            });


    }


    function addHandler(id, action, func, x) {
        var domID = document.querySelector(`#${id}`);
        domID.addEventListener(action, function (event) {
            event.preventDefault();
            func(x);
        });
    }


    function init() {
        addHandler('refresh', 'click', refresh);
        addHandler('agree', 'click', agree);
        addHandler('disagree', 'click', disagree);
        addHandler('search', 'click', getSearchData);
        /*addHandler('fujian', 'click', file);*/
    }

    init();

}());