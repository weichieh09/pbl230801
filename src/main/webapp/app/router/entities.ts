import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

// prettier-ignore
const Prod = () => import('@/entities/prod/prod.vue');
// prettier-ignore
const ProdUpdate = () => import('@/entities/prod/prod-update.vue');
// prettier-ignore
const ProdDetails = () => import('@/entities/prod/prod-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'prod',
      name: 'Prod',
      component: Prod,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'prod/new',
      name: 'ProdCreate',
      component: ProdUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'prod/:prodId/edit',
      name: 'ProdEdit',
      component: ProdUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'prod/:prodId/view',
      name: 'ProdView',
      component: ProdDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
