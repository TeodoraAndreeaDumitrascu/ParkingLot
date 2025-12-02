package com.parking.parkinglot.ejb;

import com.parking.parkinglot.common.CarDto;
import com.parking.parkinglot.common.CarPhotoDto;
import com.parking.parkinglot.entities.Car;
import com.parking.parkinglot.entities.CarPhoto;
import com.parking.parkinglot.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class CarsBean {
    private static final Logger LOG = Logger.getLogger(CarsBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    private List<CarDto> copyCarsToDto(List<Car> cars) {
        List<CarDto> carDtos = new ArrayList<>();

        for (Car car : cars) {
            CarDto carDto = new CarDto(
                    car.getId(),
                    car.getLicensePlate(),
                    car.getParkingSpot(),
                    car.getOwner().getUsername()
            );
            carDtos.add(carDto);
        }

        return carDtos;
    }

    public List<CarDto> findAllCars() {
        LOG.info("findAllCars");
        try {
            TypedQuery<Car> typedQuery = entityManager.createQuery("SELECT c FROM Car c", Car.class);

            List<Car> cars = typedQuery.getResultList();
            return copyCarsToDto(cars);
        } catch (Exception ex) {
            throw new EJBException(ex);
        }
    }
    public CarDto findById(Long carId) {
        Car car = entityManager.find(Car.class, carId);
        return new CarDto(car.getId(), car.getLicensePlate(), car.getParkingSpot(), car.getOwner().getUsername());
    }


    public void createCar(String licensePlate, String parkingSpot, Long userId) {
        LOG.info("createCar: " + licensePlate + ", " + parkingSpot + ", userId: " + userId);

        Car car = new Car();
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        // Găsim utilizatorul după ID
        User user = entityManager.find(User.class, userId);

        if (user != null) {
            car.setOwner(user);
            user.getCars().add(car);
            LOG.info("Car owner set to: " + user.getUsername());
        } else {
            LOG.warning("User not found with id: " + userId);
        }

        // Salvăm mașina în baza de date
        entityManager.persist(car);
        LOG.info("Car persisted successfully");
    }

    public void updateCar(Long carId, String licensePlate, String parkingSpot, Long userId) {
        Car car = entityManager.find(Car.class, carId);
        car.setLicensePlate(licensePlate);
        car.setParkingSpot(parkingSpot);

        // remove this car from the old owner
        User oldOwner = car.getOwner();
        oldOwner.getCars().remove(car);

        // add the car to its new owner
        User newOwner = entityManager.find(User.class, userId);
        newOwner.getCars().add(car);
        car.setOwner(newOwner);
    }

    public void deleteCarsByIds(Collection<Long> carIds) {
        LOG.info("deleteCarsByIds");

        for (Long carId : carIds) {
            Car car = entityManager.find(Car.class, carId);
            entityManager.remove(car);
        }
    }

    public void addPhotoToCar(Long carId, String filename, String fileType, byte[] fileContent) {
        LOG.info("addPhotoToCar");

        Car car = entityManager.find(Car.class, carId);
        if (car != null) {
            CarPhoto photo = new CarPhoto();
            photo.setFilename(filename);
            photo.setFileType(fileType);
            photo.setFileContent(fileContent);

            // Dacă mașina are deja o poză, șterge-o mai întâi
            if (car.getPhoto() != null) {
                entityManager.remove(car.getPhoto());
            }

            car.setPhoto(photo);
            photo.setCar(car);

            entityManager.persist(photo);
        }
    }

    public CarPhotoDto findPhotoByCarId(Integer carId) {
        List<CarPhoto> photos = entityManager
                .createQuery("SELECT p FROM CarPhoto p WHERE p.car.id = :id", CarPhoto.class)
                .setParameter("id", carId.longValue())
                .getResultList();

        if (photos.isEmpty()) {
            return null;
        }

        CarPhoto photo = photos.get(0); // ia prima fotografie
        return new CarPhotoDto(
                photo.getId(),
                photo.getFilename(),
                photo.getFileType(),
                photo.getFileContent()
        );
    }

}