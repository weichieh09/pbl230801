/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import TeamEventComponent from '@/entities/team-event/team-event.vue';
import TeamEventClass from '@/entities/team-event/team-event.component';
import TeamEventService from '@/entities/team-event/team-event.service';
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
  describe('TeamEvent Management Component', () => {
    let wrapper: Wrapper<TeamEventClass>;
    let comp: TeamEventClass;
    let teamEventServiceStub: SinonStubbedInstance<TeamEventService>;

    beforeEach(() => {
      teamEventServiceStub = sinon.createStubInstance<TeamEventService>(TeamEventService);
      teamEventServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<TeamEventClass>(TeamEventComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          teamEventService: () => teamEventServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      teamEventServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllTeamEvents();
      await comp.$nextTick();

      // THEN
      expect(teamEventServiceStub.retrieve.called).toBeTruthy();
      expect(comp.teamEvents[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      teamEventServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(teamEventServiceStub.retrieve.called).toBeTruthy();
      expect(comp.teamEvents[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      teamEventServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(teamEventServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      teamEventServiceStub.retrieve.reset();
      teamEventServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(teamEventServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.teamEvents[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      teamEventServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(teamEventServiceStub.retrieve.callCount).toEqual(1);

      comp.removeTeamEvent();
      await comp.$nextTick();

      // THEN
      expect(teamEventServiceStub.delete.called).toBeTruthy();
      expect(teamEventServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
