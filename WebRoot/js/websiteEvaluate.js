var jsonobj, xAxis1, name1, name2, name3, length1;
// 加载echart
require.config({
	paths : {
		echarts : './lib/echarts-2.2.7/build/dist'
	}
});

$("#btn_websiteEvaluate").click(
		function() {
			var date_start = document.getElementById('date1').value;
			var date_end = document.getElementById('date2').value;
			/** **************** 验证参数完整性 ************************ */
			if (date_start != undefined && date_end != undefined
					&& date_start != "" && date_end != "") {
				if (date_start <= date_end) {
					$.ajax({
						url : "./JSON/websiteEvaluate.json",
						type : "POST",
						data : {
							"startTime" : date_start,
							"endTime" : date_end,
							"userId" : '1',
							"method" : 'getWebTend'
						},
						dataType : "json",
						success : function(data) {
							console.log(data);
							status = data.status;
							message = data.message;
							if (status == 0) {
								// 处理成功，解析数据
								jsonobj = data.results;
								// 对返回数据做进一步处理
								xAxis1 = jsonobj.xAxis;
								length1 = jsonobj.xAxis.length;
								name1 = jsonobj.series[0].name;
								name2 = jsonobj.series[1].name;
								name3 = jsonobj.series[2].name;
								console.log(name1);
								// 请求成功加载热词图
								loadEchartBar(jsonobj)
							} else {
								// 打印错误信息
								console.log(message);
							}
						},
						error : function() {
							alert('请求处理不成功！');
						}
					});
				} else {
					alert('开始日期应小于结束日期！');
				}
			} else {
				alert("请正确填写日期！")
			}

		});
/*
 * // 路径配置 require.config({ paths : { echarts :
 * './lib/echarts-2.2.7/build/dist' } });
 */
function loadEchartBar(jsonobj) {
	require(// 少一个
	[ 'echarts', 'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
	], function(ec) {// 少一个
		// 基于准备好的dom，初始化echarts图表
		var myChart = ec.init(document.getElementById('div_website_eval'));

		var option = {
			grid : {
				x : 30,
				y : 150,
				x2 : 5,
				y2 : 30
			},
			title : {
				text : '南华大学舆论情况分布',
				subtext : '各媒体对南华大学新闻报道中，报导言论的褒贬情况'
			},
			tooltip : {
				trigger : 'axis'
			},
			legend : {
				data : (function() {
					// var now = new Date();
					var res = [];
					res[0] = name1;
					res[1] = name2;
					res[2] = name3;
					/*
					 * for (var i = 0; i < 3; i++) { res[i] =
					 * jsonobj.series[i].name; }
					 */
					return res;
				})()
			},
			toolbox : {
				show : true,
				feature : {
					mark : {
						show : true
					},
					dataView : {
						show : true,
						readOnly : false
					},
					magicType : {
						show : true,
						type : [ 'line', 'bar' ]
					},
					restore : {
						show : true
					},
					saveAsImage : {
						show : true
					}
				}
			},
			calculable : true,
			yAxis : [ {
				type : 'value'
			} ],
			xAxis : [ {
				type : 'category',
				data : (function() {
					// var length1 = xAxis1.length;
					// var now = new Date();
					var res = [];
					for (var i = 0; i < length1; i++) {
						res[i] = xAxis1[i];
						console.log(res[i]);
					}
					return res;
				})()
			} ],

			series : [ {
				name : name1,
				type : 'bar',
				data : (function() {
					// var length1 = jsonobj.xAxis.length;
					// var now = new Date();
					var res = [];
					for (var i = 0; i < length1; i++) {
						res[i] = jsonobj.series[0].data[i];
						// console.log(res[i]);
					}
					return res;
				})()
			}, {
				name : name2,
				type : 'bar',
				data : (function() {
					// var length1 = jsonobj.xAxis.length;
					// var now = new Date();
					var res = [];
					for (var i = 0; i < length1; i++) {
						res[i] = jsonobj.series[1].data[i];
					}
					return res;
				})()
			}, {
				name : name3,
				type : 'bar',
				data : (function() {
					// var length1 = jsonobj.xAxis.length;
					// var now = new Date();
					var res = [];
					for (var i = 0; i < length1; i++) {
						res[i] = jsonobj.series[2].data[i];
					}
					return res;
				})()
			} ]
		};
		// 为echarts对象加载数据
		myChart.setOption(option);
		// });
	});
}
