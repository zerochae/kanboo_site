import { createWebHistory, createRouter } from "vue-router"
import axios from 'axios'

import Home from './components/container/noAccess/Home.vue'
import Demo from './components/container/noAccess/Demo.vue'

import Community from './components/container/noAccess/Community.vue'
import Free from './components/component/noAccess/Community/Free.vue'
import QnA from './components/component/noAccess/Community/QnA.vue'

import Sign from './components/container/noAccess/Sign.vue'
import Projects from './components/container/access/Projects.vue'

import ProjectDetail from './components/container/access/projectDetail/ProjectDetail.vue'
import DashBoard from './components/container/access/projectDetail/DashBoard.vue'
import Scheduler from './components/container/access/projectDetail/Scheduler.vue'

import Compiler from './components/container/access/projectDetail/compiler/Compiler.vue'
import Java from './components/container/access/projectDetail/compiler/Java.vue'
import HTML from './components/container/access/projectDetail/compiler/HTML.vue'

import Board from './components/container/access/projectDetail/ProjectBoard.vue'
import Demand from './components/container/access/projectDetail/Demand.vue'
import Gantt from './components/container/access/projectDetail/GanttChart.vue'
import GitAndIssue from './components/container/access/projectDetail/GitAndIssue.vue'

import Kanban from './components/container/access/projectDetail/Kanban.vue'

import ErdAndView from './components/container/access/projectDetail/ErdAndView.vue'
import Table from './components/component/acess/projectDetail/erdAndView/erd/Table.vue'

import Setting from './components/container/access/projectDetail/Setting.vue'
import Admin from './components/container/admin/Admin.vue'

const roleCheck = (repData) => {
  axios({
    url: repData.url,
    method: repData.method,
    data: repData.data
  }).then(res => {
    if(res.data) {
      repData.next()
    } else {
      repData.next(repData.falsePath)
    }
  }).catch(() => {
    repData.next(repData.falsePath)
  })
}

const routes = [
  {
    path: '/',
    component: Home,
  },
  {
    path: '/community',
    component: Community,
    children: [
      {
        path: "free",
        component: Free
      },
      {
        path: "qna",
        component: QnA
      },
    ]
  },
  {
    path: '/signin',
    component: Sign,
  },
  {
    path: '/demo',
    component: Demo
  },
  {
    path: '/projects',
    component: Projects,
    beforeEnter: (to, from, next) => {
      const repData = {
        url : '/token/check',
        method : 'post',
        falsePath: '/signin',
        next: next,
        data: {
          token : sessionStorage.getItem("token")
        }
      }

      roleCheck(repData)
    }
  },
  {
    path: '/admin',
    component : Admin,
    // beforeEnter: (to, from, next) => {
    //   const repData = {
    //     url : '/token/admin',
    //     method : 'post',
    //     falsePath: '/signin',
    //     next: next,
    //     data: {
    //       token : sessionStorage.getItem("token")
    //     }
    //   }
    //
    //   roleCheck(repData)
    // }
  },
  {
    path: "/pdtail",
    component: ProjectDetail,
    children: [
      {
        path: "dashboard",
        component: DashBoard,
        beforeEnter: (to, from, next) => {
          const repData = {
            url : '/token/projectCheck',
            method : 'post',
            falsePath: '/projects',
            next: next,
            data: {
              projectIdx: sessionStorage.getItem("project"),
              token : sessionStorage.getItem("token")
            }
          }

          roleCheck(repData)
        }
      },
      {
        path: "scheduler",
        component: Scheduler,
        beforeEnter: (to, from, next) => {
          const repData = {
            url : '/token/projectCheck',
            method : 'post',
            falsePath: '/projects',
            next: next,
            data: {
              projectIdx: sessionStorage.getItem("project"),
              token : sessionStorage.getItem("token")
            }
          }

          roleCheck(repData)
        }
      },
      {
        path: "compiler",
        component: Compiler,
        children: [
          {
            path: "backend",
            component: Java
          },
          {
            path: "frontend",
            component: HTML
          }
        ],
        beforeEnter: (to, from, next) => {
          const repData = {
            url : '/token/projectCheck',
            method : 'post',
            falsePath: '/projects',
            next: next,
            data: {
              projectIdx: sessionStorage.getItem("project"),
              token : sessionStorage.getItem("token")
            }
          }

          roleCheck(repData)
        }
      },
      {
        path: "demand",
        component: Demand,
        beforeEnter: (to, from, next) => {
          const repData = {
            url : '/token/projectCheck',
            method : 'post',
            falsePath: '/projects',
            next: next,
            data: {
              projectIdx: sessionStorage.getItem("project"),
              token : sessionStorage.getItem("token")
            }
          }

          roleCheck(repData)
        }
      },
      {
        path: "board",
        component: Board,
        beforeEnter: (to, from, next) => {
          const repData = {
            url : '/token/projectCheck',
            method : 'post',
            falsePath: '/projects',
            next: next,
            data: {
              projectIdx: sessionStorage.getItem("project"),
              token : sessionStorage.getItem("token")
            }
          }

          roleCheck(repData)
        }
      },
      {
        path: "gantt",
        component: Gantt,
        beforeEnter: (to, from, next) => {
          const repData = {
            url : '/token/projectCheck',
            method : 'post',
            falsePath: '/projects',
            next: next,
            data: {
              projectIdx: sessionStorage.getItem("project"),
              token : sessionStorage.getItem("token")
            }
          }

          roleCheck(repData)
        }
      },
      {
        path: "gitissue",
        component: GitAndIssue,
        beforeEnter: (to, from, next) => {
          const repData = {
            url : '/token/projectCheck',
            method : 'post',
            falsePath: '/projects',
            next: next,
            data: {
              projectIdx: sessionStorage.getItem("project"),
              token : sessionStorage.getItem("token")
            }
          }

          roleCheck(repData)
        }
      },
      {
        path: "kanban",
        component: Kanban,
        beforeEnter: (to, from, next) => {
          const repData = {
            url : '/token/projectCheck',
            method : 'post',
            falsePath: '/projects',
            next: next,
            data: {
              projectIdx: sessionStorage.getItem("project"),
              token : sessionStorage.getItem("token")
            }
          }

          roleCheck(repData)
        }
      },
      {
        path: "erdview",
        component: ErdAndView,
        children: [
          {
            path: "erd",
            component: Table
          }
        ],
        beforeEnter: (to, from, next) => {
          const repData = {
            url : '/token/projectCheck',
            method : 'post',
            falsePath: '/projects',
            next: next,
            data: {
              projectIdx: sessionStorage.getItem("project"),
              token : sessionStorage.getItem("token")
            }
          }

          roleCheck(repData)
        }
      },
      {
        path: "setting",
        component: Setting,
        // beforeEnter: (to, from, next) => {
        //   const repData = {
        //     url : '/token/pmCheck',
        //     method : 'post',
        //     falsePath: '/projects',
        //     next: next,
        //     data: {
        //       projectIdx: sessionStorage.getItem("project"),
        //       token : sessionStorage.getItem("token")
        //     }
        //   }
        //
        //   roleCheck(repData)
        // }
      },
    ]
  },
  {
    path: '/logout',
    beforeEnter: (to, from, next) => {
      sessionStorage.removeItem('token')
      next('/')
    }
  },
  {
    path: "/:PathMatch(.*)*",
    redirect: "/"
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router;