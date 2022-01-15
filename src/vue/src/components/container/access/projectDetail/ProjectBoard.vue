<template>
  <div id="container">
    <div id="sub-container">
      <div id="project-board-top">
        <div>
          <button @click="[click(), step=1, this.changeUpdateCheck()]" id="project-write-btn">글 작성</button>
        </div>
        <div>
        <div class="input-container">
          <select v-model="selected" id="select">
            <option value="All">All</option>
            <option value="MEM_NICK">Writer</option>
            <option value="BOARD_CN">Content</option>
          </select>
          <input type="text" class="search-input" @keyup.enter="search" v-model="key">
          <img src="@/assets/돋보기2.png" @click="sendingSelected">
        </div>
        </div>
      </div>
      <div class="project-write-div-parent">

        <div id="project-write-div">
          <Write v-if="isOpen"
                  :step="step"
                  :category="category"/>
        </div>
        
      </div>
        <projectBoardTop/>
    </div>
  </div>
</template>

<script>
import projectBoardTop from '@/components/component/acess/projectDetail/projectBoard/projectBoardTop.vue'
import { mapMutations, mapState } from 'vuex';
import Write from '../../../component/acess/projectDetail/projectBoard/ProjectBoardWrite.vue';

export default {
  name : 'ProjectBoard',
  data() {
    return {
      key: "",
      selected : 'All',
      step : 0,
      category : "project",
      isOpen : false,
    }
  },
  components : {
    projectBoardTop,
    Write,
  },

  computed : {
  ...mapState({
    updateCheck : state => state.projectBoard.updateCheck,
    blockWrite : state => state.projectBoard.blockWrite,
    boardList : state => state.projectBoard.boardList,
    isSearch : state => state.projectBoard.isSearch,
    selected : state => state.projectBoard.selected,
  })
  },

  methods : {
    ...mapMutations({
      changeUpdateCheck : 'projectBoard/changeUpdateCheck',
      getSelectedAndKey : 'projectBoard/getSelectedAndKey',
    }),

    click(){
      this.isOpen = !this.isOpen;
      console.log(this.isOpen)
    },

    sendingSelected(){
      var object= {
        "key" : this.key,
        "selected" : this.selected
      }

      this.getSelectedAndKey(object)
    },
  },
}
</script>

<style scoped>
#project-write-btn {
  color: #fff;
  background: coral;
  border-radius: 10px;
  padding: 2px 6px;
  font-size: 16px;
}

.input-container {
  position: relative;
}

img {
  height : 20px;
  width : 20px;
  position: absolute;
  transform: translate(-50%,-50%);
  top: 50%;
  right: 5px;
}

#select {
  background-color: #414556;
  border: none;
  outline: none;
  margin-right: 10px;
  border-radius: 10px;
  height: 20px;
  font-size: 12px;
  padding-left: 5px;
}

option {
  font-size: 12px;
}

.search-input {
  border-radius: 10px;
  color : white;
  background-color: #414556;
  padding: 0 5px;
  position: relative;
  height: 20px;
  width : 160px;
  border: none;
  outline: none;
  padding-left: 10px;
  font-size: 12px;
}

#project-board-top {
 padding-right: 20vw;
  display: flex;
  justify-content: space-between;
  padding-left: 20vw;
  padding-top: 10px;
  padding-bottom: 5px;
}

#project-write-div {
  width: 60%;
  /* margin-left: 377px; */
  /* height: 80%; */
  background-color: #2C2F3B;
  margin-bottom: 20px;
  position : relative;
}
.project-write-div-parent{
  display: flex;
  justify-content: center;
}

</style>