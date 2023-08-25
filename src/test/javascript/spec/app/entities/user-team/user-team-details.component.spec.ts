/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import UserTeamDetailComponent from '@/entities/user-team/user-team-details.vue';
import UserTeamClass from '@/entities/user-team/user-team-details.component';
import UserTeamService from '@/entities/user-team/user-team.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('UserTeam Management Detail Component', () => {
    let wrapper: Wrapper<UserTeamClass>;
    let comp: UserTeamClass;
    let userTeamServiceStub: SinonStubbedInstance<UserTeamService>;

    beforeEach(() => {
      userTeamServiceStub = sinon.createStubInstance<UserTeamService>(UserTeamService);

      wrapper = shallowMount<UserTeamClass>(UserTeamDetailComponent, {
        store,
        localVue,
        router,
        provide: { userTeamService: () => userTeamServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundUserTeam = { id: 123 };
        userTeamServiceStub.find.resolves(foundUserTeam);

        // WHEN
        comp.retrieveUserTeam(123);
        await comp.$nextTick();

        // THEN
        expect(comp.userTeam).toBe(foundUserTeam);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundUserTeam = { id: 123 };
        userTeamServiceStub.find.resolves(foundUserTeam);

        // WHEN
        comp.beforeRouteEnter({ params: { userTeamId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.userTeam).toBe(foundUserTeam);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
