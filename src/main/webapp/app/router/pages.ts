import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  {
    path: '/demo/p1',
    name: 'p1',
    component: () => import('@/demo/p1/p1.vue'),
  },
  {
    path: '/demo/p2',
    name: 'p2',
    component: () => import('@/demo/p2/p2.vue'),
  },
  {
    path: '/demo/p3',
    name: 'p3',
    component: () => import('@/demo/p3/p3.vue'),
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/demo/p4',
    name: 'p4',
    component: () => import('@/demo/p4/p4.vue'),
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/demo/p4/:tId',
    name: 'p4Upate',
    component: () => import('@/demo/p4/p4-update.vue'),
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/demo/p4/:tId/p5',
    name: 'p5',
    component: () => import('@/demo/p5/p5.vue'),
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/demo/p4/:tId/p5/:pId',
    name: 'p5Upate',
    component: () => import('@/demo/p5/p5-update.vue'),
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/demo/p6',
    name: 'p6',
    component: () => import('@/demo/p6/p6.vue'),
    // meta: { authorities: [Authority.USER] },
  },
  {
    path: '/demo/p6/:eId',
    name: 'p6Upate',
    component: () => import('@/demo/p6/p6-update.vue'),
    // meta: { authorities: [Authority.USER] },
  },
]
