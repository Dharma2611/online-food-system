package com.gang.ServiceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gang.Dto.RestaurantDto;
import com.gang.Entity.Address;
import com.gang.Entity.Restaurant;
import com.gang.Entity.User;
import com.gang.Repo.AddressRpository;
import com.gang.Repo.RestaurantRepostory;
import com.gang.Repo.UserRepository;
import com.gang.Request.CreateRestaurantRequest;
import com.gang.Service.RestaurantService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RestaurnatServiceImpl implements RestaurantService {

	@Autowired
	private RestaurantRepostory rRepostory;

	@Autowired
	private AddressRpository addressRepository;
	@Autowired
	private UserRepository userRepository;

	@Override
	public Restaurant createRestaurant(CreateRestaurantRequest rRequest, User user) throws Exception {

		Address address = addressRepository.save(rRequest.getAddress());
		Restaurant restaurant = new Restaurant();
		restaurant.setAddress(address);
		restaurant.setContactInformation(rRequest.getContactInformation());
		restaurant.setCuisineType(rRequest.getCuisineType());
		restaurant.setDescription(rRequest.getDescription());
		restaurant.setImages(rRequest.getImages());
		restaurant.setName(rRequest.getName());
		restaurant.setOpeningHours(rRequest.getOpeningHours());
		restaurant.setRegistrationDate(LocalDateTime.now());
		restaurant.setOwner(user);
		return rRepostory.save(restaurant);
	}

	@Override
	public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatRestaurantRequest)
			throws Exception {
		Restaurant restaurantById = findByRestaurantId(restaurantId);
		if (restaurantById.getCuisineType() != null) {
			restaurantById.setCuisineType(updatRestaurantRequest.getCuisineType());
			if (restaurantById.getDescription() != null) {
				restaurantById.setDescription(updatRestaurantRequest.getDescription());

			}
			if (restaurantById.getName() != null) {
				restaurantById.setName(updatRestaurantRequest.getName());
			}
		}
		return rRepostory.save(restaurantById);

	}

	@Override
	public void deleteRestaurant(Long restaurantId) throws Exception {

		Restaurant byRestaurantId = findByRestaurantId(restaurantId);

		rRepostory.delete(byRestaurantId);
	}

	@Override
	public List<Restaurant> getAllRestaurant() {
		// TODO Auto-generated method stub
		return rRepostory.findAll();
	}

	@Override
	public List<Restaurant> searchRestaurant(String keyword) {
		// TODO Auto-generated method stub
		return rRepostory.findBySearchQuery(keyword);
	}

	@Override
	public Restaurant findByRestaurantId(long id) throws Exception {

		return rRepostory.findById(id).orElseThrow(() -> new Exception("Restaurant not found with ID: " + id));
	}

	@Override
	public Restaurant getRestaurantByUserId(long userId) throws Exception {
		Restaurant byOwnerId = rRepostory.findByOwnerId(userId);
		if (byOwnerId == null) {
			throw new Exception("restaurant is found with ownerId" + userId);
		}
		return byOwnerId;
	}

	@Override
	public RestaurantDto addToFavorites(long restaurantId, User user) throws Exception {
		Restaurant byRestaurantId = findByRestaurantId(restaurantId);
		RestaurantDto Dto = new RestaurantDto();
		Dto.setDescription(byRestaurantId.getDescription());
		Dto.setImages(byRestaurantId.getImages());
		Dto.setTitle(byRestaurantId.getName());
		Dto.setId(restaurantId);
		boolean isFevorited = false;
		List<RestaurantDto> favorites = user.getFavorites();
		for (RestaurantDto restaurantDto : favorites) {
			if (restaurantDto.getId().equals(restaurantId)) {
				isFevorited = true;
				break;

			}
		}
		if(isFevorited) {
			favorites.removeIf(restaurantDto ->restaurantDto.getId().equals(restaurantId));
		}else {
			favorites.add(Dto);
		}
		userRepository.save(user);

		return Dto;
	}

	@Override
	public Restaurant updateRestaurantStatus(long id) throws Exception {
		Restaurant byRestaurantId = findByRestaurantId(id);
		byRestaurantId.setOpen(!byRestaurantId.isOpen());
		return rRepostory.save(byRestaurantId);
	}

}
