<template>
  <div class="erd-container">

    <SideBar  :topTitle="this.$store.state.erd.sideBarData.title"
              :top="true"
              :bottom="false"
              :topPath="this.$store.state.erd.sideBarData.path"
              :topData="this.$store.state.erd.sideBarData.topData"
              :isTree="false"
              :textColor="this.$store.state.erd.sideBarData.textColor"/>

    <router-view></router-view>

    <ShowQuery v-if="this.$store.state.erd.isShowQuery"/>
  </div>
</template>

<script>
import { mapMutations } from 'vuex'
import SideBar from '../../../component/acess/projectDetail/erdAndView/SideBar.vue'
import ShowQuery from '../../../component/acess/projectDetail/erdAndView/erd/ShowQuery.vue'

export default {
  name: "erd",
  data() {
    return {
      
    }
  },
  components: {
    SideBar,
    ShowQuery
  },
  methods: {
    ...mapMutations({
    })
  },
  mounted() {
  }
}
</script>

<style scoped>
.erd-container {
  height: calc(100vh - 70px);
  display: flex;
  position: relative;
}

</style>