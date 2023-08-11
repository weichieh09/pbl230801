/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import EventPlayerComponent from '@/entities/event-player/event-player.vue';
import EventPlayerClass from '@/entities/event-player/event-player.component';
import EventPlayerService from '@/entities/event-player/event-player.service';
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
  describe('EventPlayer Management Component', () => {
    let wrapper: Wrapper<EventPlayerClass>;
    let comp: EventPlayerClass;
    let eventPlayerServiceStub: SinonStubbedInstance<EventPlayerService>;

    beforeEach(() => {
      eventPlayerServiceStub = sinon.createStubInstance<EventPlayerService>(EventPlayerService);
      eventPlayerServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<EventPlayerClass>(EventPlayerComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          eventPlayerService: () => eventPlayerServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      eventPlayerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllEventPlayers();
      await comp.$nextTick();

      // THEN
      expect(eventPlayerServiceStub.retrieve.called).toBeTruthy();
      expect(comp.eventPlayers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      eventPlayerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(eventPlayerServiceStub.retrieve.called).toBeTruthy();
      expect(comp.eventPlayers[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      eventPlayerServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(eventPlayerServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      eventPlayerServiceStub.retrieve.reset();
      eventPlayerServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(eventPlayerServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.eventPlayers[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      eventPlayerServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(eventPlayerServiceStub.retrieve.callCount).toEqual(1);

      comp.removeEventPlayer();
      await comp.$nextTick();

      // THEN
      expect(eventPlayerServiceStub.delete.called).toBeTruthy();
      expect(eventPlayerServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
