<template>
  <div class="head-main">
      <table class="demand-table">
        <tr class="demand-table-tr">
          <td class="demand-table-th-chk-td">
            <input id="headCheckBox" type="checkbox" @change="clickAllCheckBox(this)" class="headCheckBox">
            <label for="headCheckBox"/>
          </td>
          <td class="demand-table-th-no">No</td>
          <td class="demand-table-th-category">Category</td>
          <td class="demand-table-th-id">Demand ID</td>
          <td class="demand-table-th-name">Demand Name</td>
          <td class="demand-table-th-detail">Demand Description</td>
          <td class="demand-table-th-requester">Requester</td>
          <td class="demand-table-th-remark">Remark</td>
        </tr>
      </table>
  </div>
</template>

<script>
import {mapMutations} from "vuex";
export default {

name : "DemandHead",

  methods : {
    ...mapMutations({
      save: "demand/save",
      clickAllCheckBox : "demand/clickAllCheckBox"
    }),


  }
}
</script>

<style scoped>
.head-main{
  height : 100%;
}

.demand-table{
  margin: 0 auto;
  width : 95%;
  height : 100%;
  text-align: center;
  font-size: 16px;
  color: #fff;
  box-shadow: 0px -1px 0px 1px #999;
  border-radius: 5px 5px 0 0;

}

.demand-table-tr{
  height : 100%;
  vertical-align : middle;
  text-align: center;

}

.demand-table-th-chk-td{
  width: 1.5%;
}

.demand-table-tr td {
  border: 1px solid #999;
}

input[type="checkbox"] + label {
  display: inline-block;
  width: 20px;
  height: 20px;
  overflow: hidden;
  cursor: pointer;
}

input[type="checkbox"]:checked + label {
  background-color: lightslategray;
  border-radius: 50%;
}

input[type="checkbox"] {
  display: none;
}

.demand-table-th-no{
  width : 3%;
  vertical-align : middle;
  text-align: center;
  padding: 10px 0;

}

.demand-table-th-category{
  width : 10%;
  vertical-align : middle;
  text-align: center;
  padding: 10px 0;
}

.demand-table-th-id{
  width : 10%;
  vertical-align : middle;
  text-align: center;
  padding: 10px 0;
}

.demand-table-th-name{
  width : 15%;
  vertical-align : middle;
  text-align: center;
  padding: 10px 0;
}

.demand-table-th-detail{
  width : 41%;
  vertical-align : middle;
  text-align: center;
  padding: 10px 0;
}

.demand-table-th-requester{
  width : 10%;
  vertical-align : middle;
  text-align: center;
  padding: 10px 0;
}

.demand-table-th-remark{
  width : 10%;
  vertical-align : middle;
  text-align: center;
  padding: 10px 0;
}



</style>