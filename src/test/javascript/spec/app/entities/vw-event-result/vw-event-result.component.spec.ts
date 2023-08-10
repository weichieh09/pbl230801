/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import VwEventResultComponent from '@/entities/vw-event-result/vw-event-result.vue';
import VwEventResultClass from '@/entities/vw-event-result/vw-event-result.component';
import VwEventResultService from '@/entities/vw-event-result/vw-event-result.service';
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
  describe('VwEventResult Management Component', () => {
    let wrapper: Wrapper<VwEventResultClass>;
    let comp: VwEventResultClass;
    let vwEventResultServiceStub: SinonStubbedInstance<VwEventResultService>;

    beforeEach(() => {
      vwEventResultServiceStub = sinon.createStubInstance<VwEventResultService>(VwEventResultService);
      vwEventResultServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<VwEventResultClass>(VwEventResultComponent, {
        store,
        localVue,
        stubs: { jhiItemCount: true, bPagination: true, bModal: bModalStub as any },
        provide: {
          vwEventResultService: () => vwEventResultServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      vwEventResultServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllVwEventResults();
      await comp.$nextTick();

      // THEN
      expect(vwEventResultServiceStub.retrieve.called).toBeTruthy();
      expect(comp.vwEventResults[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should load a page', async () => {
      // GIVEN
      vwEventResultServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();

      // THEN
      expect(vwEventResultServiceStub.retrieve.called).toBeTruthy();
      expect(comp.vwEventResults[0]).toEqual(expect.objectContaining({ id: 123 }));
    });

    it('should not load a page if the page is the same as the previous page', () => {
      // GIVEN
      vwEventResultServiceStub.retrieve.reset();
      comp.previousPage = 1;

      // WHEN
      comp.loadPage(1);

      // THEN
      expect(vwEventResultServiceStub.retrieve.called).toBeFalsy();
    });

    it('should re-initialize the page', async () => {
      // GIVEN
      vwEventResultServiceStub.retrieve.reset();
      vwEventResultServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.loadPage(2);
      await comp.$nextTick();
      comp.clear();
      await comp.$nextTick();

      // THEN
      expect(vwEventResultServiceStub.retrieve.callCount).toEqual(3);
      expect(comp.page).toEqual(1);
      expect(comp.vwEventResults[0]).toEqual(expect.objectContaining({ id: 123 }));
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
      vwEventResultServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(vwEventResultServiceStub.retrieve.callCount).toEqual(1);

      comp.removeVwEventResult();
      await comp.$nextTick();

      // THEN
      expect(vwEventResultServiceStub.delete.called).toBeTruthy();
      expect(vwEventResultServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});