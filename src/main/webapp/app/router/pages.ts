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
    meta: { authorities: [Authority.USER] },
  },
]
