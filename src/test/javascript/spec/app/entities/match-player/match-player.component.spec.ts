/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import MatchPlayerComponent from '@/entities/match-player/match-player.vue';
import MatchPlayerClass from '@/entities/match-player/match-player.component';
import MatchPlayerService from '@/entities/match-player/match-player.service';
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
  describe('MatchPlayer Management Component', () => {
    let wrapper: Wrapper<MatchPlayerClass>;
    let comp: MatchPlayerClass;
    let matchPlayerServiceStub: SinonStubbedInstance<MatchPlayerService>;

    beforeEach(() => {
      matchPlayerServiceStub = sinon.createStubInstance<MatchPlayerService>(MatchPlayerService);
      matchPlayerServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<MatchPlayerClass>(MatchPlayerComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          matchPlayerService: () => matchPlayerServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      matchPlayerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllMatchPlayers();
      await comp.$nextTick();

      // THEN
      expect(matchPlayerServiceStub.retrieve.called).toBeTruthy();
      expect(comp.matchPlayers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      matchPlayerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(matchPlayerServiceStub.retrieve.called).toBeTruthy();
      expect(comp.matchPlayers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      matchPlayerServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(matchPlayerServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      matchPlayerServiceStub.retrieve.reset();
      matchPlayerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(matchPlayerServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.matchPlayers[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      matchPlayerServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(matchPlayerServiceStub.retrieve.callCount).toEqual(1);

      comp.removeMatchPlayer();
      await comp.$nextTick();

      // THEN
      expect(matchPlayerServiceStub.delete.called).toBeTruthy();
      expect(matchPlayerServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
