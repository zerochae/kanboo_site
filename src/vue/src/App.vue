<template>
  <div class="container">
    <nav>
      <Menu/>
<!--      <Notice />-->
      <NoticeList v-if="$store.state.menu.isOpenNotification"/>
      <UserSetting v-if="$store.state.menu.isOpenUserSetting"/>
     </nav>
    <router-view></router-view>
<!--    <Chat v-if="$store.state.global.chatOn"/>-->
  </div>
</template>

<script>
import '../public/reset.css'
import Menu from './components/component/global/Menu.vue'
// import Notice from './components/popup/Notice.vue'
import NoticeList from './components/popup/NoticeList.vue'
import UserSetting from './components/popup/UserSetting.vue'
// import Chat from './components/popup/Chat.vue'

export default {
  name: 'App',
  components: {
    Menu,
    // Notice,
    NoticeList,
    UserSetting,
    // Chat
  }
}
</script>

<style scoped>

.container {
  width: 100%;
  display: flex;
  flex-direction: column;
  background: #16161A;
}

nav {
  width: 100%;
  height: 70px;
  background: #75757529;
  color: #ffffff;
}

</style>
