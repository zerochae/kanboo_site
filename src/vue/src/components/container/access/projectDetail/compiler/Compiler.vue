<template>
  <div class="compiler-container">
    <SideBar/>
    <router-view></router-view>
    <AddModal v-if="this.$store.state.javaCompile.isOpen"/>
  </div>
</template>

<script>
import SideBar from '../../../../component/acess/projectDetail/compiler/SideBar.vue'
import AddModal from "@/components/component/acess/projectDetail/compiler/AddModal";

export default {
  name: "Compiler",
  data() {
    return {

    }
  },
  components: {
    SideBar,
    AddModal
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