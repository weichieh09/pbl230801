/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TeamPlayerComponent from '@/entities/team-player/team-player.vue';
import TeamPlayerClass from '@/entities/team-player/team-player.component';
import TeamPlayerService from '@/entities/team-player/team-player.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.component('jhi-sort-indicator', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('TeamPlayer Management Component', () => {
    let wrapper: Wrapper<TeamPlayerClass>;
    let comp: TeamPlayerClass;
    let teamPlayerServiceStub: SinonStubbedInstance<TeamPlayerService>;

    beforeEach(() => {
      teamPlayerServiceStub = sinon.createStubInstance<TeamPlayerService>(TeamPlayerService);
      teamPlayerServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TeamPlayerClass>(TeamPlayerComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          teamPlayerService: () => teamPlayerServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      teamPlayerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTeamPlayers();
      await comp.$nextTick();

      // THEN
      expect(teamPlayerServiceStub.retrieve.called).toBeTruthy();
      expect(comp.teamPlayers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      teamPlayerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(teamPlayerServiceStub.retrieve.called).toBeTruthy();
      expect(comp.teamPlayers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      teamPlayerServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(teamPlayerServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      teamPlayerServiceStub.retrieve.reset();
      teamPlayerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(teamPlayerServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.teamPlayers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should calculate the sort attribute for an id', () => {
      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['id,asc']);
    });

    it('should calculate the sort attribute for a non-id attribute', () => {
      // GIVEN
      comp.propOrder = 'name';

      // WHEN
      const result = comp.sort();

      // THEN
      expect(result).toEqual(['name,asc', 'id']);
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      teamPlayerServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(teamPlayerServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTeamPlayer();
      await comp.$nextTick();

      // THEN
      expect(teamPlayerServiceStub.delete.called).toBeTruthy();
      expect(teamPlayerServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
