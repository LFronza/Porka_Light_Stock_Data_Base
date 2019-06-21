/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { LoteDeleteDialogComponent } from 'app/entities/lote/lote-delete-dialog.component';
import { LoteService } from 'app/entities/lote/lote.service';

describe('Component Tests', () => {
    describe('Lote Management Delete Component', () => {
        let comp: LoteDeleteDialogComponent;
        let fixture: ComponentFixture<LoteDeleteDialogComponent>;
        let service: LoteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [LoteDeleteDialogComponent]
            })
                .overrideTemplate(LoteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(LoteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LoteService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
