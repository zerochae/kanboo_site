<template>
  <div class="list-container">
    <div id="modal" v-if="this.IsModalOpen === true">
      <Modal/>
    </div>
    <div class="project-state">
      <div id="project-top">
        <div id="img-div">
          <img v-if="member.memImg === null" id="profile-img" src="@/assets/profile.png" alt="profile">
          <img v-else id="profile-img" :src="member.memImg">
        </div>
        <div id="nick-div">
          {{ member.memNick }}
        </div>
      </div>
      <div id="profile-list">
        <div>
          <div>
            Proceddings( {{ this.$store.state.projectList.progressLength }} )
          </div>
          <div class="projects-div-ul">
            <ul class="projects-ul">
              <li v-for="(item, index) in projectList" :key="index">
                <div v-if="item.prjctComplAt === 'n'">
                                <span>
                                    <i class="far fa-circle"></i>
                                </span>
                  <span class="test-span">
                                    <a @click="moveToDashBoard(item)">{{ item.prjctNm }}</a>
                                </span>
                </div>
              </li>
            </ul>
          </div>
        </div>
        <div>
          <div class="project-completed">
            Completed( {{ this.$store.state.projectList.completeLength }} )
          </div>
          <div class="projects-div-ul">
            <div>
              <ul class="projects-ul">
                <li v-for="(item, index) in projectList" :key="index">
                  <div v-if="item.prjctComplAt === 'y'">
                                    <span>
                                        <i class="far fa-circle"></i>
                                    </span>
                    <span class="test-span">
                                        <a @click="moveToDashBoard(item)">{{ item.prjctNm }} </a>
                                    </span>
                  </div>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="list-right-container">
      <div id="addproject-div">
        <AddProjects/>
      </div>
      <div id="changes-div">
        <Changes/>
      </div>
    </div>
  </div>
</template>

<script>
import AddProjects from '@/components/component/acess/projects/AddProjects.vue'
import Changes from '@/components/component/acess/projects/LatestChanges.vue'
import Modal from '@/components/popup/CreateProject.vue'
import {mapMutations, mapState} from 'vuex'

export default {
  name: 'ProjectList',
  data() {
    return {
      progressLength: 0,
      completeLength: 0,
    }
  },
  computed: {
    ...mapState({
      IsModalOpen: state => state.projectList.IsModalOpen,
      projectList: state => state.projectList.projectList,
      member: state => state.projectList.member
    })
  },

  methods: {
    ...mapMutations({
      pushToProjectList: 'projectList/pushToProjectList',
      moveToDashBoard: 'global/moveToDashBoard',
      listRest: 'projectList/listRest'
    }),

    getProjectsList() {
      this.axios.get('/pdtail/allList', {
        params: {
          token: sessionStorage.getItem('token')
        }
      }).then(e => {
        this.pushToProjectList(e.data)
      })
    }
  },
  components: {
    AddProjects,
    Changes,
    Modal,
  },
  mounted() {
    this.getProjectsList()
  },
  unmounted() {
    this.listRest()
  }
}
</script>

<style scoped>
.list-container {
  color: white;
  display: flex;
  height: calc(100vh - 70px);
}

.list-top-container {
  margin-top: 20px;
}

#profile-img {
  width: 50px;
  height: 50px
}

#nick-div {
  margin-left: 16px;
  height: 50px;
  width: fit-content;
  font-size: 30px;
  padding-top: 10px;
}

.project-state {
  padding-top: 20px;
  padding-left: 30px;
  display: flex;
  flex-direction: column;
}

#profile-list {
  display: flex;
  flex-direction: column;
  margin-top: 30px;
}

#project-top {
  display: flex;
  padding-top: 40px;
}

.projects-div-ul {
  margin-left: 20px;
  margin-top: 8px;
  height: 140px;
  overflow: scroll;
  -ms-overflow-style: none;
  scrollbar-width: none;
}

.projects-div-ul::-webkit-scrollbar {
  display: none;
}

#completed-div {
  margin-top: 10px;
}

.projects-ul a {
  color: #fff;
}

.test-span {
  margin-left: 10px;
}

.projects-ul li {
  margin-top: 10px;
}

.project-completed {
  margin-top: 10px;
}

.list-right-container {
  display: flex;
  flex-direction: column;
  width: 90%;
  height: calc(100vh - 70px);
}

#addproject-div {
  height: 60%;
}

#changes-div {
  font-size: 30px;
  width: 100%;
  padding-left: 40px;
}
</style>