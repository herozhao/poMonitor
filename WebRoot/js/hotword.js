var jsonobj, status, message;
// 加载echart
require.config({
    paths : {
        echarts : './lib/echarts-2.2.7/build/dist'
    }
});

$("#btn_hotword").click(
        function() {
            var date_start = document.getElementById('date1').value;
            var date_end = document.getElementById('date2').value;
            /** **************** 验证参数完整性 ************************ */
            if (date_start != undefined && date_end != undefined
                    && date_start != "" && date_end != "") {
                if (date_start<=date_end){
                $.ajax({
                    url : "./servlet/HotWordsServlet",
                    type : "POST",
                    data : {
                         "startTime":date_start,
                         "endTime":date_end,
                        //"startTime" : '2012-09-10',
                        //"endTime" : '2013-01-10',
                        // 默认先给1，后期需要自动获得
                        "userId" : '1',
                        "method" : 'getHotWords'
                    },
                    dataType : "json",
                    success : function(data) {
                        console.log(JSON.stringify(data));
                        status = data.status;
                        message = data.message;
                        if (status == 0) {
                            // 处理成功，解析数据
                            jsonobj = data.results;
                            // 对返回数据做进一步处理

                            // 请求成功加载热词图
                            loadEchartForce(jsonobj)
                        } else {
                            // 打印错误信息
                            console.log(message);
                        }

                    },
                    error : function() {
                        alert("请求处理不成功！");
                    }
                });
            }else{
                alert('开始时间应小于结束时间！');
            }
            } else {
                alert("请正确填写日期！")
            }

        });

// 填充数据，加载force图
function loadEchartForce(jsonobj) {
    require(
    // 少一个
    [ 'echarts', 'echarts/chart/force' // 使用柱状图就加载bar模块，按需加载
    ],

            function(ec) {// 少一个
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('main'));
                var option = {// 少一个
                    title : {
                        text : '热词统计',
                        x : 'right',
                        y : 'bottom'
                    },
                    tooltip : {
                        trigger : 'item',
                        formatter : '{a} : {b}'
                    },
                    toolbox : {
                        show : true,
                        feature : {
                            restore : {
                                show : true
                            },
                            magicType : {
                                show : true,
                                type : [ 'force', 'chord' ]
                            },
                            saveAsImage : {
                                show : true
                            }
                        }
                    },
                    legend : {
                        x : 'left',
                        data : [ '褒', '中', '贬' ]
                    },
                    series : [ {
                        type : 'force',
                        name : "热词统计",
                        ribbonType : false,
                        categories : [ {
                            name : '褒'
                        }, {
                            name : '中'
                        }, {
                            name : '贬'
                        } ],
                        itemStyle : {
                            normal : {
                                label : {
                                    show : true,
                                    textStyle : {
                                        color : '#333'
                                    }
                                },
                                nodeStyle : {
                                    brushType : 'both',
                                    borderColor : 'rgba(255,215,0,0.4)',
                                    borderWidth : 1
                                },
                                linkStyle : {
                                    type : 'line'
                                //color:'#5182ab'
                                }
                            },
                            emphasis : {
                                label : {
                                    show : false
                                // textStyle: null // 默认使用全局文本样式，详见TEXTSTYLE
                                },
                                nodeStyle : {
                                // r: 30
                                },
                                linkStyle : {}
                            }
                        },
                        useWorker : false,
                        //minRadius : 15,
                        //maxRadius : 25,
                        gravity : 1.1,
                        scaling : 1.1,
                        roam : 'move',

                        nodes : (function() {
                            var length1 = jsonobj.nodes.length;
                            // var now = new Date();
                            function fun(category, name, value, label, index) {
                                this.category = category;
                                this.name = name;
                                this.value = value;
                                this.index = index;
                                if (label == 1)
                                    this.label = this.name + "\n" + "敏感词";
                            }

                            // var res = new Object();
                            var res = [];
                            for (var i = 0; i < length1; i++) {
                                // console.log(jsonobj.nodes[i].category);
                                res[i] = new fun(jsonobj.nodes[i].category,
                                        jsonobj.nodes[i].name,
                                        jsonobj.nodes[i].value,
                                        jsonobj.nodes[i].label,
                                        jsonobj.nodes[i].index);

                            }
                            return res;
                        })(),
                        links : (function() {
                            var length1 = jsonobj.links.length;

                            function fun(source, target, weight) {
                                this.source = source;
                                this.target = target;
                                this.weight = weight;

                            }

                            // var res = new Object();
                            var res = [];
                            for (var i = 0; i < length1; i++) {
                                res[i] = new fun(jsonobj.links[i].source,
                                        jsonobj.links[i].target,
                                        jsonobj.links[i].weight);
                                // console.log(res[i]);
                            }
                            return res;
                        })()
                    } ]
                };
                // 为echarts对象加载数据
                myChart.setOption(option);
            });
}

