/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { ApresentacaoDeleteDialogComponent } from 'app/entities/apresentacao/apresentacao-delete-dialog.component';
import { ApresentacaoService } from 'app/entities/apresentacao/apresentacao.service';

describe('Component Tests', () => {
    describe('Apresentacao Management Delete Component', () => {
        let comp: ApresentacaoDeleteDialogComponent;
        let fixture: ComponentFixture<ApresentacaoDeleteDialogComponent>;
        let service: ApresentacaoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [ApresentacaoDeleteDialogComponent]
            })
                .overrideTemplate(ApresentacaoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApresentacaoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApresentacaoService);
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
