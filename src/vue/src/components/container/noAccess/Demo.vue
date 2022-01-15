<template>
  <div class="compiler-container">
    <SideBar  :top="true"
              :bottom="false"
              :topTitle="topTitle"
              :bottomTitle="bottomTitle"
              :topButton="false"
              :bottomButton="true"
              :isTree="true"
              :topPath="topPath"
              :bottomPath="bottomPath"
              :textColor="textColor"
              :topData="topData"
              :bottomData="bottomData"/>
    <Java />
  </div>
</template>

<script>
import SideBar from '../../component/acess/projectDetail/erdAndView/SideBar.vue'
import compileData from "@/assets/compiler.json"
import arrayToTree from 'array-to-tree'
import Java from '@/components/component/noAccess/demo/Java.vue'

export default {
  name: "Compiler",
  data() {
    return {
      topTitle: "java",
      bottomTitle: "html",
      topPath: "/demo",
      textColor: "#fff",
      topData: arrayToTree(compileData, {childrenProperty: 'nodes'}),
    }
  },
  components: {
    SideBar,
    Java
  }
}
</script>

<style scoped>
.compiler-container {
  height: calc(100vh - 70px);
  color: #fff;
  display: flex;
}
</style>