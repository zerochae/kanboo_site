<template>
  <div class="main">
    <div class="demand-table-div" id ="demand-table-div">
      <table class="demand-table" id="demand-table">
        <tr class="demand-table-tr" v-for="(item, index) in this.$store.state.demand.rows" :key="index">
          <td class="demand-table-td-chk-td" >
            <input :id="`demand-chk-${item.demandCnIdx}`" class="demand-table-chk" type="checkbox"
                   @change="clickCheckBox(item.demandCnIdx)" :value="`demand-chk-${item.demandCnIdx}`">
          <label class="demand-table-td-label" :for="`demand-chk-${item.demandCnIdx}`" />
          </td>
          <td class="demand-table-td-no"><input :id="`demand-no-${item.demandCnNum}`"
                                                @keyup.enter="save" class="demand-table-td-input" type="text"
                                                v-model="item.demandCnNum">
          </td>
          <td class="demand-table-td-category"><input :id="`demand-category-${item.demandCnNum}`"
                                                      @keyup.enter="save" class="demand-table-td-input" type="text"
                                                      v-model="item.demandCnSe">
          </td>
          <td class="demand-table-td-id"><input :id="`demand-demandId-${item.demandCnNum}`"
                                                @keyup.enter="save" class="demand-table-td-input" type="text"
                                                v-model="item.demandCnId">
          </td>
          <td class="demand-table-td-name"><input :id="`demand-demandName-${item.demandCnNum}`"
                                                  @keyup.enter="save" class="demand-table-td-input" type="text"
                                                  v-model="item.demandCnNm">
          </td>
          <td class="demand-table-td-detail"><input :id="`demand-detail-${item.demandCnNum}`"
                                                    @keyup.enter="save" class="demand-table-td-input" type="text"
                                                    v-model="item.demandCnDetail">
          </td>
          <td class="demand-table-td-requester"><input :id="`demand-requester-${item.demandCnNum}`"
                                                       @keyup.enter="save" class="demand-table-td-input" type="text"
                                                       v-model="item.demandCnRequstNm">
          </td>
          <td class="demand-table-td-remark"><input :id="`demand-remark-${item.demandCnNum}`"
                                                    @keyup.enter="save" class="demand-table-td-input" type="text"
                                                    v-model="item.demandCnRm">
          </td>
        </tr>
      </table>
    </div>

  </div>
</template>

<script>
import {mapMutations} from "vuex";

export default {
  name: "DemandTable",
  data() {
    return {

    }
  },
  methods: {
    ...mapMutations({
      save: "demand/save",
      clickCheckBox : "demand/clickCheckBox",

    }),
  }
}
</script>

<style scoped>


.demand-table-div {
  height: 100%;
}

.demand-table {
  width: 100%;
  text-align: center;
  border: 1px solid #999;
}

.demand-table-tr {
  height: 4.5vh;
  padding: 10px 0;
  border: 1px solid #999;
}

.demand-table-td-chk-td{
  width: 1.5%;
}

.demand-table-td-input {
  border: 0;
  width: 95%;
  height: 4.8vh;
  background: none;
  cursor: default;
  outline: none;
  font-size: 16px;
  text-align: center;
  color: #fff;
}

.demand-table-td-no {
  width: 3%;
  border: 1px solid #999;
}

.demand-table-td-category {
  width: 10%;
  border: 1px solid #999;
}

.demand-table-td-id {
  width: 10%;
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

.demand-table-td-name {
  width: 15%;
  border: 1px solid #999;
}

.demand-table-td-detail {
  width: 41%;
  border: 1px solid #999;
}

.demand-table-td-requester {
  width: 10%;
  border: 1px solid #999;
}

.demand-table-td-remark {
  width: 10%;
}

</style>