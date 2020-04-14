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
        ajax: {
            url: "/admin/userList",
            data: {
                page: 1,
                limit: 10
            },
            dataSrc: function (data) {
                return data.rows;
            }
        },
        columns: [
            {
                'data': 'username',
                sortable: true
            },
            {
                'data': 'roleid',
                sortable: true,
                "render":function (data,type,row,meta) {
                    if(row.roleId==3){
                        return "普通用户";
                    } else if (row.roleId == 1){
                        return "管理员";
                    }else if (row.roleId == 4){
                        return "VIP用户";
                    } else {
                        return "测试";
                    }
                }
            },
            {
                'data': 'balance',
                sortable: true

            }, {
                'data': 'status' ,
                "render":function (data,type,row,meta) {
                    if(row.status ==0){
                        return "使用中";
                    } else if (row.status == 1)
                    {
                        return "禁用";
                    }else {
                        return "禁用";
                    }
                }
            },{
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
