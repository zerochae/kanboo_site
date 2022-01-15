<template>
  <div class="table-container">
    <Write class="add-table"/>
    <div class="zoom-btn">
      <button type="button" @click="zoomIn" class="in">
        <i class="fa fa-search-plus" style="color: #fff;"></i>
      </button>
      <button type="button" @click="zoomOut" class="out">
        <i class="fa fa-search-minus" style="color: #fff;"></i>
      </button>
    </div>
    <ul class="table-list">
      <li class="table-box"
          v-for="(item, index) in this.$store.state.erd.sideBarData.topData"
          :key="index"
          @click="clickErd(item)"
          :style="{
            border: item.borderColor,
            transform: `scale(${this.$store.state.erd.scale})`
            }">
        <div class="title">
          <input type="text" :readonly="!item.isModify" v-model="item.name"> 
          <div>
            <button @click.stop="deleteTable(item)"
                    v-if="!item.isModify"
                    type="button"
                    class="table-del-btn">Del</button>
            <button @click.stop="showModify(item)"
                    v-if="!item.isModify"
                    type="button"
                    class="table-mod-btn">Mod</button>
            <button @click.stop="modifyTable(item)"
                    v-if="item.isModify"
                    type="button"
                    class="table-mod-btn">finish</button>
          </div>
        </div>
        <ul class="body">
          <li v-for="col of item.columns"
              :key="col"
              class="col-wrap">
            <input @click.stop
                  type="text"
                  placeholder="name" 
                  :readonly="!item.isModify" 
                  class="name" 
                  v-model="col.name">
            <input @click.stop
                  type="text"
                  placeholder="type" 
                  :readonly="!item.isModify" 
                  class="type" 
                  v-model="col.type">
            <input @click.stop
                  type="text"
                  placeholder="constraint" 
                  :readonly="!item.isModify" 
                  class="constraint" 
                  v-model="col.constraint">
          </li>
        </ul>
      </li>
    </ul>
  </div>
</template>

<script>
import { mapMutations, mapActions } from 'vuex'
import Write from '../erd/Write.vue'

export default {
  name: "table",
  data() {
    return {
      isModify: true
    }
  },
  components: {
    Write
  },
  methods: {
    ...mapMutations({
      deleteTable: 'erd/deleteTable',
      showModify: 'erd/showModify',
      cancel: 'erd/cancel',
      modifyTable: 'erd/modifyTable',
      clickErd: 'erd/clickErd',
      zoomIn: 'erd/zoomIn',
      zoomOut: 'erd/zoomOut'
    }),
    ...mapActions({
      getErdData: 'erd/getErdData'
    }),
  },
  mounted() {
    this.getErdData()
  }
}
</script>

<style scoped>
.table-container {
  border: 1px solid #999;
  width: 85%;
  position: relative;
}

.add-table {
  position: absolute;
  top: 10px;
  right: 10px;
  z-index: 9999;
}

.table-list {
  height: 100%;
  padding: 30px;
  overflow: scroll;
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
  grid-auto-rows: auto;
  grid-gap: 10px;
}

.table-box {
  width: fit-content;
  padding: 5px 10px;
  background: #2C2F3B;
  color: #fff;
  margin-bottom: 10px;
  height: fit-content;
}

.table-box .title {
  margin-bottom: 10px;
  font-size: 20px;
  display: flex;
  justify-content: space-between;
}

.table-del-btn,
.table-mod-btn {
  color: #999;
  transition: color .1s ease-in;
}

.table-del-btn:hover,
.table-mod-btn:hover {
  color: #fff;
}

.col-wrap {
  display: flex;
}

.title input,
.col-wrap input {
  border: none;
  outline: none;
  background: none;
  color: #fff;
}

.table-box .body span {
  min-width: 100px;
}

.zoom-btn {
  position: absolute;
}
</style>