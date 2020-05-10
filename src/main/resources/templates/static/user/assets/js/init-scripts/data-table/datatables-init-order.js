(function ($) {
    //    "use strict";


    /*  Data Table
    -------------*/

    $('#bootstrap-data-table').DataTable({
        lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "All"]]
    });

    $('#bootstrap-data-table-export').DataTable({
        lengthMenu: [1, 2, 5, 7, 10],
        // 每页的初期件数 用户可以操作lengthMenu上的值覆盖
        pageLength: 5,
        buttons: ['copy', 'csv', 'excel', 'pdf', 'print'],
        dataType: "json",
        oPaginate: {
            sFirst: "首页",
            sLast: "末页",
            sNext: "下页",
            sPrevious: "上页"
        },
        oLanguage:{
            sInfo: "第 _START_ 到 _END_ 条记录，共 _TOTAL_ 条",
            sInfoEmpty: "第 0 到 0 条记录，共 0 条",
            sLoadingRecords: "正在载入...",
            sProcessing: "正在载入...",
            sSearch: "搜索:",
            sZeroRecords: "没有相关记录",
            oPaginate: {
                sFirst: "首页",
                sLast: "末页",
                sNext: "下页",
                sPrevious: "上页"
            }
        },
        autoWidth: true,
        deferRender: true,
        ajax: {
            url: "/base/order/list",
            dataSrc: function (data) {
                return data;
            }
        },
        columns: [
            {
                'data': 'username',
                sortable: true
            },
            {
                'data': 'plateCode',
                sortable: true
            },
            {
                'data': 'parkingName',
                sortable: true
            },
            {
                'data': 'carportNum',
                sortable: true
            },
            {
                'data': 'orderCarportTime',
                sortable: true

            }, {
                'data': 'orderCarportStatus' ,
                "render":function (data,type,row,meta) {
                    if(row.orderCarportStatus ==0){
                        return "预约中";
                    } else if (row.orderCarportStatus == 1)
                    {
                        return "已完成";
                    }else {
                        return "已违约";
                    }
                }
            },{
            "data":null,"render":function (data,type,row, meta) {

                    var methodType = "update";
                    var html="<button type='button' class='btn btn-primary' data-toggle='modal' data-target='#exampleModalCenter' onclick='showDiv("+JSON.stringify(row)+","+JSON.stringify(methodType)+")'>修改</button>"
                    return html;
                }
            }

        ]
    });

	$('#row-select').DataTable( {
        initComplete: function () {
				this.api().columns().every( function () {
					var column = this;
					var select = $('<select class="form-control"><option value=""></option></select>')
						.appendTo( $(column.footer()).empty() )
						.on( 'change', function () {
							var val = $.fn.dataTable.util.escapeRegex(
								$(this).val()
							);
							column
								.search( val ? '^'+val+'$' : '', true, false )
								.draw();
						} );

					column.data().unique().sort().each( function ( d, j ) {
						select.append( '<option value="'+d+'">'+d+'</option>' )
					} );
				} );
			}
		} );

})(jQuery);
