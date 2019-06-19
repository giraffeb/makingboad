<%@ page pageEncoding="utf-8" %>

    </div><%--END_DIV: content_area--%>
</div><%--END_DIV: main_area--%>
<footer>
    <div id="footer_area" class="d-block p-2 bg-light text-center"><%--END_DIV: footer_area--%>
        <p>copyright giraffeb</p>
    </div><%--END_DIV: footer_area--%>
</footer>

<%--table event --%>
<script>
    function table_row_click_event(){
        let table = document.getElementById("post_list_table");
        if(table === null){
            return;
        }

        let rows = table.getElementsByTagName("tr");

        for(let row of rows){
            row.addEventListener("click", (evt => location.href = row.getAttribute("href")) );
        }
    }

    table_row_click_event();
</script>
</body>
</html>