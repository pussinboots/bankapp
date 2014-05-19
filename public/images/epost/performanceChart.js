

var colors = ['#FF9900', '#333333'];
var completeChartData;
var completeFieldData;

/** Dummy Data **/
//var chartData = [{"year":2010,"month":11,"day":21,"views":5148,"clicks":491,"clickscomm":0,"transrej":2,"transcommrej":35.25,"trans":9,"transcomm":169,"ecpm":32.828282828283,"ecpc":0.34419551934827},{"year":2010,"month":11,"day":22,"views":3992,"clicks":452,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":9,"transcomm":155,"ecpm":38.827655310621,"ecpc":0.3429203539823},{"year":2010,"month":11,"day":23,"views":3547,"clicks":438,"clickscomm":0,"transrej":1,"transcommrej":65,"trans":8,"transcomm":117,"ecpm":32.9856216521,"ecpc":0.26712328767123},{"year":2010,"month":11,"day":24,"views":3328,"clicks":413,"clickscomm":0,"transrej":1,"transcommrej":65,"trans":15,"transcomm":246,"ecpm":73.918269230769,"ecpc":0.5956416464891},{"year":2010,"month":11,"day":25,"views":3384,"clicks":432,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":7,"transcomm":97.5,"ecpm":28.812056737589,"ecpc":0.22569444444444},{"year":2010,"month":11,"day":26,"views":3080,"clicks":380,"clickscomm":0,"transrej":1,"transcommrej":35,"trans":9,"transcomm":249,"ecpm":80.844155844156,"ecpc":0.65526315789474},{"year":2010,"month":11,"day":27,"views":3271,"clicks":398,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":8,"transcomm":140,"ecpm":42.800366860287,"ecpc":0.35175879396985},{"year":2010,"month":11,"day":28,"views":3481,"clicks":386,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":15,"transcomm":230.5,"ecpm":66.216604424016,"ecpc":0.59715025906736},{"year":2010,"month":11,"day":29,"views":3886,"clicks":474,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":14,"transcomm":255.5,"ecpm":65.748841996912,"ecpc":0.53902953586498},{"year":2010,"month":11,"day":30,"views":3750,"clicks":528,"clickscomm":0,"transrej":1,"transcommrej":35,"trans":8,"transcomm":113,"ecpm":30.133333333333,"ecpc":0.21401515151515},{"year":2010,"month":12,"day":1,"views":3201,"clicks":405,"clickscomm":0,"transrej":1,"transcommrej":14,"trans":16,"transcomm":271.5,"ecpm":84.817244611059,"ecpc":0.67037037037037},{"year":2010,"month":12,"day":2,"views":2994,"clicks":424,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":6,"transcomm":126.52,"ecpm":42.257849031396,"ecpc":0.29839622641509},{"year":2010,"month":12,"day":3,"views":3202,"clicks":404,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":11,"transcomm":174,"ecpm":54.341036851968,"ecpc":0.43069306930693},{"year":2010,"month":12,"day":4,"views":2682,"clicks":338,"clickscomm":0,"transrej":3,"transcommrej":66,"trans":5,"transcomm":67.5,"ecpm":25.167785234899,"ecpc":0.19970414201183},{"year":2010,"month":12,"day":5,"views":3341,"clicks":431,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":8,"transcomm":90.5,"ecpm":27.087698293924,"ecpc":0.20997679814385},{"year":2010,"month":12,"day":6,"views":3464,"clicks":405,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":13,"transcomm":211.68,"ecpm":61.108545034642,"ecpc":0.52266666666667},{"year":2010,"month":12,"day":7,"views":3444,"clicks":394,"clickscomm":0,"transrej":1,"transcommrej":30,"trans":8,"transcomm":137,"ecpm":39.779326364692,"ecpc":0.34771573604061},{"year":2010,"month":12,"day":8,"views":3486,"clicks":407,"clickscomm":0,"transrej":1,"transcommrej":12,"trans":11,"transcomm":145,"ecpm":41.594951233505,"ecpc":0.35626535626536},{"year":2010,"month":12,"day":9,"views":4093,"clicks":367,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":3,"transcomm":80,"ecpm":19.545565599805,"ecpc":0.21798365122616},{"year":2010,"month":12,"day":10,"views":3121,"clicks":373,"clickscomm":0,"transrej":1,"transcommrej":21,"trans":9,"transcomm":135.5,"ecpm":43.415571932073,"ecpc":0.36327077747989},{"year":2010,"month":12,"day":11,"views":2924,"clicks":386,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":6,"transcomm":76,"ecpm":25.991792065663,"ecpc":0.19689119170984},{"year":2010,"month":12,"day":12,"views":3903,"clicks":454,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":18,"transcomm":235,"ecpm":60.210094798873,"ecpc":0.51762114537445},{"year":2010,"month":12,"day":13,"views":5343,"clicks":542,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":15,"transcomm":228,"ecpm":42.672655811342,"ecpc":0.42066420664207},{"year":2010,"month":12,"day":14,"views":3785,"clicks":504,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":10,"transcomm":128.5,"ecpm":33.949801849406,"ecpc":0.25496031746032},{"year":2010,"month":12,"day":15,"views":2988,"clicks":489,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":10,"transcomm":155,"ecpm":51.874163319946,"ecpc":0.31697341513292},{"year":2010,"month":12,"day":16,"views":3206,"clicks":465,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":5,"transcomm":49.56,"ecpm":15.458515283843,"ecpc":0.10658064516129},{"year":2010,"month":12,"day":17,"views":3088,"clicks":315,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":16,"transcomm":209,"ecpm":67.681347150259,"ecpc":0.66349206349206},{"year":2010,"month":12,"day":18,"views":2881,"clicks":327,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":12,"transcomm":236,"ecpm":81.916001388407,"ecpc":0.7217125382263},{"year":2010,"month":12,"day":19,"views":3617,"clicks":408,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":14,"transcomm":274.5,"ecpm":75.891622891899,"ecpc":0.67279411764706},{"year":2010,"month":12,"day":20,"views":3559,"clicks":454,"clickscomm":0,"transrej":0,"transcommrej":0,"trans":9,"transcomm":120.5,"ecpm":33.857825231807,"ecpc":0.26541850220264}];
//var fields = {
//		transcomm: {label: "Commission (open, approved, confirmed)", units: "EUR", total: "4933,26 EUR", change: "+15%"},
//		trans: {label: "Transactions (open, approved, confirmed)", units: "# of transactions", total: "311", change: "+5%"},
//	ecpc: {label: "eCPC", units: "EUR", total: "0.38 EUR", change: "+19%"},
//		ecpm: {label: "eCPM (view)", units: "EUR", total: "43.17 EUR", change: "+26%"},
//		clickscomm: {label: "Clicks Commission", units: "EUR", total: "0.00 EUR", change: "0%"},
//		views: {label: "Views", units: "# of views", total: "114273", change: "-8%"},
//		clicks: {label: "Clicks", units: "# of clicks", total: "12965", change: "-3%"},
//		transcommrej: {label: "Commission (rejected)", units: "EUR", total: "448,25 EUR", change: "-39%"},
//		transrej: {label: "Transactions (rejected)", units: "# of transactions", total: "15", change: "-67%"}
//};

function findObjInArray(arr, obj) {
  for(var i=0; i<arr.length; i++) {
    if (arr[i][obj] != undefined) return arr[i][obj];
  }
}

function setPerformanceData(cData,fData, lData) {
	completeChartData = eval(cData);
	completeFieldData = eval(fData);
	localizationData = $.parseJSON(lData);	
	
	setCurrencySelectValues();
}

function drawChart(chartDataCurrency, fieldsCurrency) {

	//var i = 0;
	var series = [];
	var yAxes = [];

    var chartType = "";
    $('.chartTypesControl input:radio:checked').each(function(i,e){ chartType = e.value; });
    
    var dataToDisplay = [];

    switch(chartType) {
      case  'transactions' : {
        dataToDisplay = [ 'trans', 'transcomm' ];
        break;
      }
      case  'views' : {
        dataToDisplay = [ 'views', 'clicks' ];
        break;
      }
      case  'ecpc' : {
        dataToDisplay = [ 'ecpm', 'ecpc' ];
        break;
      }
      case  'clicks' : {
        dataToDisplay = [ 'clicks', 'trans' ];
        break;
      }
    }

    for ( var i = 0; i < 2; i++ ) {

        var format = true;
  	    colorNum = i;

		// y axis
		yAxes[i] = {
	      title: {
	          text: findObjInArray(fieldsCurrency, dataToDisplay[i]).units
	          
	      },
	      labels: {
				formatter: function() {
					return Highcharts.numberFormat(this.value, 2);
				}
	      },
	      gridLineWidth: 1,
	      min:0
	    };

	    if (colorNum == 1) {
	    	yAxes[i].opposite = true;
	    }

	    // series
	    chartdata = [];
		$.each(chartDataCurrency, function(j,item) {
			date = Date.UTC(item.year, item.month - 1, item.day);
			chartdata.push([date, item[dataToDisplay[i]]]);
		});
		
	    series[i] = {
	    	name: findObjInArray(fieldsCurrency, dataToDisplay[i]).label,
			type: 'line',
			yAxis: i,
			color: colors[colorNum],
			data: chartdata,
			formatValue: format
		};
    }

	/*
	$('.chartTypeControl input:radio:checked').each(function() {
		var format = true;
		
		radioButtonId = $(this).attr('id');
		
		colorNum = radioButtonId.substring(radioButtonId.length-1);
		id = radioButtonId.substring(0,radioButtonId.length-1);
	
		// y axis
		yAxes[i] = {
	      title: {
	          text: findObjInArray(fieldsCurrency, id).units
	      },
	      gridLineWidth: 0,
	      min:0
	    };
    
	    if (colorNum == 1) {
	    	yAxes[i].opposite = true;
	    }
	    
	    switch (id)
	    {
	    	case "views":
	    		format = false;
		    	break;
	    	case "clicks":
	    		format = false;
		    	break;
	    	case "trans":
	    		format = false;
		    	break;
		    default:  	
		}
    
	    // series
	    chartdata = [];
		$.each(chartDataCurrency, function(i,item) {
			date = Date.UTC(item.year, item.month - 1, item.day);
			chartdata.push([date, item[id]]);
		});
		
	    series[i] = {
	    	name: findObjInArray(fieldsCurrency, id).label,
			type: 'line',
			yAxis: i,
			color: colors[colorNum],
			data: chartdata,
			formatValue: format
		};
		
		i++;
	});
*/
	
	// chart options
	var options = {

		chart: {
			renderTo: 'chart-container',
			defaultSeriesType: 'line',
			borderRadius : 0,
			marginTop:20
		},

		credits: {
			enabled: false
		},
		title: {
			text: ''
		},
		legend: {
			enabled: false
		},
		tooltip: {
			formatter: function() {
				var point1 = this.points[0];
				var point2 = this.points[1];
				
				return '<span style="color:' + point1.series.color + ';">' + point1.series.name +'</span>:<b>'+ checkValueFormatting(point1) + '</b><br/>'
				+ '<span style="color:' + point2.series.color + ';">' + point2.series.name +'</span>:<b>'+ checkValueFormatting(point2) + '</b>';
			},
			shared: true
		},
		xAxis: {
			type: 'datetime',
			gridLineWidth: 0,
			labels : {
				rotation: 315,
				y : 25
			},
			tickLength: 10
		},
		yAxis: yAxes,
        plotOptions: {
            series: {
                marker: {
                    enabled: false,
                    states: {
                        hover: {
                            enabled: true
                        }
                    }
                }
            }
        },
		series: series
	};		


    Highcharts.setOptions({
        
    	lang: {
    		months: localizationData.months.split(","),
    		decimalPoint: localizationData.decimalPoint
    	},
    	
    	chart: {
				style: {
					fontFamily: 'Arial, Verdana, Helvetica, sans-serif', // default font
					fontSize: '10px',
					border : '1px solid #D6D6D6',
					backgroundColor: '#FFFFFF',
					padding: '5px'
				}
        }
	});

	chart = new Highcharts.Chart(options);     
}

function checkValueFormatting(point) {
	if(point.series.options.formatValue) {
		return Highcharts.numberFormat(point.y, 2);
	}
	else {
		return point.y;
	}
}

function prepareDataForSelectedCurrency(){
	var fields;
	var chartdata;
	var selectedCurrency = $("#currencySelect").val();
	
	$.each(completeFieldData, function(key, item) {
		$.each(item, function(key, value){
			if(key == selectedCurrency) {
				fields = value;
			}
		});
	});
	
	$.each(fields, function(key, item) {
		$.each(item, function(key, value){
			$("#" + key + "Container").text(value.total +" "+value.performanceUnit);
			$("#" + key + "Change").text("("+value.change + "%)");
			$("#" + key + "Change").removeClass("percentageInfoPos percentageInfoNeg");
			if(parseFloat(value.change) > 0) {
				$("#" + key + "Change").addClass("percentageInfoPos");
			}
			
			if(parseFloat(value.change) < 0) {
				$("#" + key + "Change").addClass("percentageInfoNeg");
			}
		});
 	});
	
	$.each(completeChartData, function(key, item) {
		$.each(item, function(key, value){
			if(key == selectedCurrency) {
				chartdata = value;
			}
		});
	});
	
	drawChart(chartdata, fields);
}

function setCurrencySelectValues() {
	var count=0;
	$.each(completeFieldData, function(key, item) {
		$.each(item, function(key, value){
			var optionElem = $('<option value="'+key+'">'+key+'</option>');
			
			if(key == 'EUR'){
				optionElem.attr('selected',true);
			}
			optionElem.appendTo('#currencySelect');
			count++;
		});
	});
	if(count > 1) {
		$('#currencySelect').attr('style', 'display:block');
	}
	
	prepareDataForSelectedCurrency();
}

