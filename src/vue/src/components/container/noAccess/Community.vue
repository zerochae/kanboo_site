<template>
  <div class="head-container">
    <div class="container">
      <div class="real-container">
        <div class="left-container">
          <router-link @click="changeCodeDetail(7);
                            emptyInputBox();
                            step=0;
                            category='7';
                            "
                       class ="board-direction" to="/community/free">자유게시판</router-link>
          <router-link @click="changeCodeDetail(8);
                            emptyInputBox();
                            step=0;
                            category='8';
                            "
                       class ="board-direction" to="/community/qna">문의 게시판</router-link>
          <button  @click="[changeWriteIsOpen(), step=1, this.changeUpdateCheck()]" class="board-direction" :disabled="blockWrite == true">글 작성</button>
        </div>
        <div class="input-container">
          <select v-model="selected" id="select" @change="sendingSelected">
            <option value="All">All</option>
            <option value="memNick">Writer</option>
            <option value="boardCN">Content</option>
          </select>
          <input type="text" class="search-input" @keyup.enter="sendingSelected" v-model="key">
          <img src="@/assets/돋보기2.png" @click="sendingSelected">
        </div>
      </div>
      <div class="body-router">
        <div id="write-div">
          <Write v-if="this.isOpen"
                 :step="step"
                 :category="category"/>
        </div>
        <router-view></router-view>
      </div>
    </div>
  </div>
</template>

<script>
import Write from '@/components/component/noAccess/Community/BoardWrite.vue';
import {mapActions, mapMutations, mapState} from 'vuex';

export default {
  data(){
    return {
      key: "",
      selected : 'All',
      step : 0,
      category : "7",
      // isOpen : false,
    }
  },

  components: {
    Write,
  },
  computed : {
    ...mapState({
      updateCheck : state => state.community.updateCheck,
      blockWrite : state => state.community.blockWrite,
      boardList : state => state.community.boardList,
      isSearch : state => state.community.isSearch,
      selected : state => state.community.selected,
      isOpen : state => state.community.isOpen,
    })
  },

  methods: {
    ...mapMutations({
      changeUpdateCheck : 'community/changeUpdateCheck',
      getSelectedAndKey : 'community/getSelectedAndKey',
      changeCodeDetail : 'community/changeCodeDetail',
      resetData: 'community/resetData',
      changeWriteIsOpen : 'community/changeWriteIsOpen',
    }),

    ...mapActions({
      getBoardList: "community/getBoardList",
      getBoardNum : 'community/getBoardNum',
    }),

    click(){
      this.isOpen = !this.isOpen;
      console.log(this.isOpen)
    },

    sendingSelected() {
      this.resetData()
      const object= {
        "key" : this.key,
        "selected" : this.selected
      }
      const position = this.$route.fullPath.split('/')[2]
      this.getSelectedAndKey(object)
      this.getBoardNum(position)
      this.getBoardList(position)
    },

    emptyInputBox(){
      // document.querySelector('.search-input').value = null
      this.key = ''
      this.selected = 'All'
      const object= {
        "key" : this.key,
        "selected" : this.selected
      }
      this.getSelectedAndKey(object)
      this.resetData()
      this.getBoardNum(null)

      this.getBoardList(null)
    },

    writeAfterReload(){
      const position = this.$route.fullPath.split('/')[2]
      if(position === 'qna'){
        this.category = '8'
        console.log(this.category)
      } else if(position === 'free'){
        this.category = '7'
        console.log(this.category)
      }
    },

  },
  watch: {
    '$route' () {
      this.resetData()
    }
  },
  mounted() {
    const position = this.$route.fullPath.split('/')[2]
    console.log(position)
    this.getBoardList(position)
    this.getBoardNum(position)


    this.writeAfterReload()
  }
}
</script>

<style scoped>
.container {
  width: 60vw;
}

input {
  color: #000;
}

.head-container {
  display: flex;
  justify-content: center;
  padding-top: 10px;
  gap: 20px;
  width: 100vw;
  height: calc(100vh - 70px);
  overflow: hidden;
}

.real-container {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
}

.board-direction {
  background-color: coral;
  border-radius: 10px;
  padding: 2px 16px;
  font-size: 16px;
  margin-right: 10px;
  cursor: pointer;
}

.board-direction:last-child {
  margin: 0;
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

.write-div {
  width: 60vw;
  height: 80%;
  background-color: #2C2F3B;
  margin-bottom: 20px;
  position : relative;
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
</style>