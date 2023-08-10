/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import TeamPlayerDetailComponent from '@/entities/team-player/team-player-details.vue';
import TeamPlayerClass from '@/entities/team-player/team-player-details.component';
import TeamPlayerService from '@/entities/team-player/team-player.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('TeamPlayer Management Detail Component', () => {
    let wrapper: Wrapper<TeamPlayerClass>;
    let comp: TeamPlayerClass;
    let teamPlayerServiceStub: SinonStubbedInstance<TeamPlayerService>;

    beforeEach(() => {
      teamPlayerServiceStub = sinon.createStubInstance<TeamPlayerService>(TeamPlayerService);

      wrapper = shallowMount<TeamPlayerClass>(TeamPlayerDetailComponent, {
        store,
        localVue,
        router,
        provide: { teamPlayerService: () => teamPlayerServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundTeamPlayer = { id: 123 };
        teamPlayerServiceStub.find.resolves(foundTeamPlayer);

        // WHEN
        comp.retrieveTeamPlayer(123);
        await comp.$nextTick();

        // THEN
        expect(comp.teamPlayer).toBe(foundTeamPlayer);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundTeamPlayer = { id: 123 };
        teamPlayerServiceStub.find.resolves(foundTeamPlayer);

        // WHEN
        comp.beforeRouteEnter({ params: { teamPlayerId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.teamPlayer).toBe(foundTeamPlayer);
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
