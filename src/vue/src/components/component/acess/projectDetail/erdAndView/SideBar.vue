<template>
  <div class="side-bar-container">
    <div class="top-tab" v-if="top" :style="{height: topHeight}">
      <h2 class="title">
        <img src="../../../../../assets/documentIcon.png" alt="/">
        <router-link :to="topPath"
                      :style="{ color: textColor }">
          {{topTitle}}
        </router-link>
        <button class="add-java-btn" 
                type="button"
                v-if="topButton">+</button>
      </h2>
      <div class="directory" v-if="isTree">
        <Tree :nodes="dataObj"/>
      </div>
      <ul class="directory" v-if="!isTree">
        <li class="table-title">
          <span>TABLES</span>
          <button class="export-btn"
                  @click="exportQuery">
            <img src="../../../../../assets/export.png" alt="">
          </button>
        </li>
        <li class="table-name"
            v-for="item in topData" 
            :key="item"
            :style="{color: textColor}">
          {{item.name}}
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
import Tree from 'vue3-tree'
import "vue3-tree/dist/style.css";
import compileData from "../../../../../assets/compiler.json"
import arrayToTree from 'array-to-tree'
import { mapMutations } from 'vuex';

export default {
  name: "sideBar",
  components: {
    Tree
  },
  data() {
    return {
      dataObj : arrayToTree(compileData, {childrenProperty: 'nodes'}),
      show: "",
      topHeight: "50%"
    }
  },
  props: {
    topTitle: String,
    bottomTitle: String,
    top: Boolean,
    bottom: Boolean,
    topButton: Boolean,
    bottomButton: Boolean,
    topPath: String,
    bottomPath: String,
    topData: Array,
    bottomData: Array,
    isTree: Boolean,
    textColor: String
  },
  mounted() {
    if(!this.isTree) {
      this.show = "none"
      this.topHeight = "100%"
    }
  },
  methods: {
    ...mapMutations({
      exportQuery: 'erd/exportQuery'
    })
  }
}
</script>

<style scoped>
.side-bar-container {
  width: 15%;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.side-bar-container > * {
  height: 50%;
}

button {
  color: #fff;
}

.title {
  padding: 10px 10px 10px 20px;
  display: flex;
  align-items: center;
  border-bottom: 1px solid #999;
  border-top: 1px solid #999;
}

.title img {
  margin-right: 10px;
}

.title button {
  margin-left: auto;
}

.directory {
  height: calc(100% - 46px);
  overflow: scroll;
}

ul.directory {
  padding: 10px;
  font-size: 20px;
}

.table-title {
  color: #fff;
  font-weight: bold;
  margin-bottom: 20px;
  width: 100%;
  display: flex;
  justify-content: space-between;
}

.table-name {
  padding-left: 20px;
  cursor: pointer;
  color: #999;
  transition: color .1s ease-in;
}

.table-name:hover {
  color: #fff;
}

</style>