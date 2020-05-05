(function ($) {
    //    "use strict";


    /*  Data Table
    -------------*/

    $('#bootstrap-data-table').DataTable({
        lengthMenu: [[10, 20, 50, -1], [10, 20, 50, "All"]]
    });

    $('#bootstrap-data-table-export').DataTable({
        lengthMenu: [[10, 25, 50, -1], [10, 25, 50, "All"]],
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
        ajax: {
            url: "/user/plate/list",
            dataSrc: function (data) {
                return data;
            }
        },
        columns: [
            {
                'data': 'plateCode',
                sortable: true
            },
            {
            "data":null,"render":function (data,type,row, meta) {
                    console.log(row);

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
