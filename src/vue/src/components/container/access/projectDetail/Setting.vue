<template>
  <div class="setting-container">
    <Name/>
    <Date/>

    <div class="addMember-div" v-if="this.$store.state.setting.clickState == true">
      <AddMember/>
    </div>

    <Member />

    <footer>
      <Buttons/>
    </footer>
  </div>

</template>
<script>
import AddMember from '@/components/component/acess/projectDetail/setting/AddMember.vue'
import Name from '@/components/component/acess/projectDetail/setting/Name.vue'
import Date from '@/components/component/acess/projectDetail/setting/Date.vue'
import Member from '@/components/component/acess/projectDetail/setting/Member.vue'
import Buttons from '@/components/component/acess/projectDetail/setting/Buttons.vue'


export default {
  components : {
    Name,
    Date,
    AddMember,
    Member,
    Buttons,
  },
}

</script>

<style scoped>
.setting-container{
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-direction: column;
  overflow: scroll;
  overflow-x: hidden;
  overflow-y: auto;
  width : 100vw;
  height : 100vh;
  background-color: #16161A;
}
.setting-container::-webkit-scrollbar{
  display: none;
}
</style>