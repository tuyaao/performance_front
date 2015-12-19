Date.prototype.Format = function (day,fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate()+day, //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

$(document).ready(function(){
	var startDateTextBox = $('#rest_example_4_start');
    var endDateTextBox = $('#rest_example_4_end');
    startDateTextBox.val((new Date(new Date()-8*24*60*60*1000)).Format(0,"MM/dd/yyyy hh:mm:ss"));
    endDateTextBox.val((new Date()).Format(0,"MM/dd/yyyy hh:mm:ss"));
    startDateTextBox.datetimepicker({
        timeFormat: 'HH:mm:ss',
        stepHour: 1,
        stepMinute: 1,
        stepSecond: 5,
        onClose: function(dateText, inst) {
            if (endDateTextBox.val() != '') {
                var testStartDate = startDateTextBox.datetimepicker('getDate');
                var testEndDate = endDateTextBox.datetimepicker('getDate');
                if (testStartDate >= testEndDate){
                    endDateTextBox.datetimepicker('setDate', testStartDate);
//                    endDateTextBox.val("");
                }

            }
            var selectendtime = $("#rest_example_4_end").val();
            var selectstarttime = $("#rest_example_4_start").val();
            var timediff = Date.parse(selectendtime)-Date.parse(selectstarttime);
            if(timediff > 691200000){//时间超过8天，毫秒
            	$("#termsnull").removeClass("hidden");
            }else{
            	$("#termsnull").addClass("hidden");
            }
            /* else {
                endDateTextBox.val(dateText);
            } */
        },
//        onSelect: function (selectedDateTime){
//            endDateTextBox.datetimepicker('option', 'minDate', startDateTextBox.datetimepicker('getDate') );
//        }
    });
    endDateTextBox.datetimepicker({
    	timeFormat: 'HH:mm:ss',
        stepHour: 1,
        stepMinute: 1,
        stepSecond: 5,
        defaultValue: new Date(),
        onClose: function(dateText, inst) {
            if (startDateTextBox.val() != '') {
                var testStartDate = startDateTextBox.datetimepicker('getDate');
                var testEndDate = endDateTextBox.datetimepicker('getDate');
//                alert(testStartDate);
//                alert(testEndDate);
                if (testStartDate > testEndDate){
                    startDateTextBox.datetimepicker('setDate', testEndDate);
//                    startDateTextBox.val("");
                }
            }
            var selectendtime = $("#rest_example_4_end").val();
            var selectstarttime = $("#rest_example_4_start").val();
            var timediff = Date.parse(selectendtime)-Date.parse(selectstarttime);
            if(timediff > 691200000){//时间超过8天，毫秒
            	$("#termsnull").removeClass("hidden");
            }else{
            	$("#termsnull").addClass("hidden");
            }
            /* else {
                startDateTextBox.val(dateText);
            } */
        },
//        onSelect: function (selectedDateTime){
//            startDateTextBox.datetimepicker('option', 'maxDate', endDateTextBox.datetimepicker('getDate') );
//        }
    });
});