import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  {
    path: '/pbl/wcc101',
    name: 'wcc101',
    component: () => import('@/pbl/wcc101/wcc101.vue'),
  },
  {
    path: '/pbl/wcc201',
    name: 'wcc201',
    component: () => import('@/pbl/wcc201/wcc201.vue'),
  },
  {
    path: '/pbl/wcc301',
    name: 'wcc301',
    component: () => import('@/pbl/wcc301/wcc301.vue'),
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/pbl/wcc401',
    name: 'wcc401',
    component: () => import('@/pbl/wcc401/wcc401.vue'),
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/pbl/wcc401/:tId',
    name: 'wcc401Upate',
    component: () => import('@/pbl/wcc401/wcc401-update.vue'),
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/pbl/wcc401/:tId/wcc501',
    name: 'wcc501',
    component: () => import('@/pbl/wcc501/wcc501.vue'),
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/pbl/wcc401/:tId/wcc501/:pId',
    name: 'wcc501Upate',
    component: () => import('@/pbl/wcc501/wcc501-update.vue'),
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/pbl/wcc601',
    name: 'wcc601',
    component: () => import('@/pbl/wcc601/wcc601.vue'),
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/pbl/wcc601/:eId',
    name: 'wcc601Upate',
    component: () => import('@/pbl/wcc601/wcc601-update.vue'),
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/pbl/wcc701',
    name: 'wcc701',
    component: () => import('@/pbl/wcc701/wcc701.vue'),
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/pbl/wcc801',
    name: 'wcc801',
    component: () => import('@/pbl/wcc801/wcc801.vue'),
    meta: { authorities: [Authority.USER] },
  },
]
