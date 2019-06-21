/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PorkaLightStockDataBaseTestModule } from '../../../test.module';
import { GrupoDeleteDialogComponent } from 'app/entities/grupo/grupo-delete-dialog.component';
import { GrupoService } from 'app/entities/grupo/grupo.service';

describe('Component Tests', () => {
    describe('Grupo Management Delete Component', () => {
        let comp: GrupoDeleteDialogComponent;
        let fixture: ComponentFixture<GrupoDeleteDialogComponent>;
        let service: GrupoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [PorkaLightStockDataBaseTestModule],
                declarations: [GrupoDeleteDialogComponent]
            })
                .overrideTemplate(GrupoDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(GrupoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GrupoService);
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
