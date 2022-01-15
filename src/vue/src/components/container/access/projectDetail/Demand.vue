<template>
  <div class="main-div">
    <div class="main-top-div">
      <div class="project-name-div">{{$store.state.demand.projectName}}</div>
      <div class="modify-date-div">Last Modified Data : {{$store.state.demand.modifiedDate}}</div>
      <div class="main-top-right-div">
        <select class="download-select" @change="down($event)">
          <option class="download-select-option" value="none">Export</option>
          <option class="download-select-option" value="PDF">PDF</option>
          <option class="download-select-option" value="Excel">Excel</option>
        </select>
        <button @click="save" class="addRow-btn">저장</button>
        <button @click="addRow" class="addRow-btn">추가</button>
        <button @click="deleteRow" class="addRow-btn">삭제</button>
        <label for="uploadFile" class="uploadFile-label">업로드</label>
        <input id="uploadFile" type="file" @change="uploadFile" class="uploadFile"/>
      </div>
    </div>
    <div class="main-bottom-div">
      <div class="demand-head">
        <DemandHead/>
      </div>
      <div class="demand-container" id="demand-container">
        <DemandTable/>
      </div>
    </div>
  </div>
</template>

<script>

import {mapMutations} from "vuex";
export default {
  name : "Demand",
  components: {
    DemandTable,
    DemandHead
  },
  data() {
    return {

    }
  },
  methods: {
    ...mapMutations({
      addRow : "demand/addRow",
      save : "demand/save",
      load : "demand/load",
      uploadFile : "demand/uploadFile",
      down : "demand/down",
      deleteRow : "demand/deleteRow",
      // testPdf : "demand/testPdf",
      // addDefaultRow : "demand/addDefaultRow"
    }),


  },
  mounted() {
    this.load()
    // this.addDefaultRow()
  }
}

import DemandHead from '@/components/component/acess/projectDetail/demand/DemandHead.vue'
import DemandTable from '@/components/component/acess/projectDetail/demand/DemandTable.vue'
</script>

<style scoped>

.main-div{
  height : calc(100vh - 70px);
  overflow: hidden;
  padding-top : 20px;
}
.main-top-div{
  display: flex;
  flex-direction: column;
  width: 95%;
  margin: 0 auto 20px;
}

.main-top-right-div{
  width : 500px;
  justify-self: end;
  display: flex;
  justify-content: space-between;
  align-items: center;
  align-self: flex-end;
}

.main-bottom-div{
  display: flex;
  flex-direction: column;

}
.download-select{
  background: none;


}

.download-select-option{
  color: white;
}


.project-name-div{
  font-size: 24px;
  color: white;
  margin-bottom: 10px;

}

.modify-date-div{
  font-size: 18px;
  color: white;
  white-space:nowrap;
  margin-left: 20px;
}

.demand-container{
  margin : auto;
  width: 95%;
  height : 70vh;
  border-bottom: 1px solid #999;
  overflow :scroll;
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */

}

.uploadFile-label{
  background-color: gray;
  width: 42px;
  height: 22px;
}

.uploadFile{
  display : none;
}


.demand-container::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}

.demand-head{
}
.addRow-btn{
  background: orange;
  color : white;
  border-radius: 5px;
}

.download-select{
  background: black;
  color: white;
  border-radius: 5px;
}



</style>