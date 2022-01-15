<template>

  <ul class="nav-container">
    <li>
      <router-link to="/" v-if="menuList.left.type === 'img'">
        <img src="../../../assets/kanboo_logo.png" alt="logo">
      </router-link>
      <router-link :to="menuList.left.path" v-if="menuList.left.type === 'text'">
        {{menuList.left.name}}
      </router-link>
    </li>
    <li>
      <ul class="left-menu">
        <li v-for="item in menuList.right" :key="item">
          <router-link v-if="item.path === nowTab" 
                      class="route-menu check" 
                      :to="item.path">{{item.name}}</router-link>
          <button type="button" v-else-if="item.name === 'notification'"
                  class="alarm-icon"
                  @click="notificationClick">
                  <i class="fa fa-bell-o"></i>
          </button>
          <button type="button" v-else-if="item.name === 'userSetting'"
                  class="user-icon"
                  @click="userSettingClick">
                  <i class="fa fa-user-o"></i>
          </button>
          <router-link v-else-if="item.path !== nowTab" 
                      class="route-menu" 
                      :to="item.path">{{item.name}}</router-link>
        </li>
      </ul>
    </li>
  </ul>  

</template>

<script>
import { mapMutations } from 'vuex'
import MenuContent from '../../../assets/MenuList.json'
export default {
  name: 'menu',
  data() {
    return {
      list: MenuContent,
      menuList: {
        "left": {},
        "right": []
      },
      nowTab: ""
    }
  },
  methods: {
    ...mapMutations({
      notificationClick: 'menu/notificationClick',
      userSettingClick: 'menu/userSettingClick'
    })
  },
  watch: {
    '$route' (to) {
      const path = to.path
      this.nowTab = path
      const obj = {
        "left": {},
        "right": []
      }
      
      // console.log(this.$store.state.global.isLogin)

      if(path.includes('pdtail')) {
        obj.left = this.list.projectDetail.left
        obj.right = this.list.projectDetail.right
      } else {
        obj.left = this.list.noAccess.left
      }

      switch(path) {
        case '/':
          obj.right = this.list.noAccess.home
          break
        case '/community/free':
        case '/community/qna':
          obj.right = this.list.noAccess.community
          break
        case '/signin':
          obj.right = this.list.noAccess.sign
          break
        case '/demo':
          obj.right = this.list.noAccess.home
          break
        case '/projects':
          this.list.access.right.push({
            "name": "userSetting",
            "path": "#",
            "type": "img"
          })
          obj.right = this.list.access.right
          break
      }

      if(this.$store.state.global.isLogin) {
        obj.right = this.list.access.right
      }

      this.menuList = obj
    }
  },
  
}
</script>

<style scoped>
.nav-container {
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 30px;
}

img {
  width: 100px;
}

button {
  color: #fff;
}

.left-menu {
  display: flex;
}

.route-menu {
  padding: 0 10px;
  color: #999;
  transition: color .2s ease-in;
}

.route-menu:hover {
  color: #fff;
}

.check {
  color: #fff;
}

.fa {
  color: #999;
  transition: color .2s ease-in;
}

.fa:hover {
  color: #fff;
}
</style>