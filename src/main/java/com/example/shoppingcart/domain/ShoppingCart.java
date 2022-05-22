package com.example.shoppingcart.domain;

import com.example.shoppingcart.ShoppingCartApi;
import com.google.protobuf.Empty;
import kalix.javasdk.eventsourcedentity.EventSourcedEntity;
import kalix.javasdk.eventsourcedentity.EventSourcedEntity.Effect;
import kalix.javasdk.eventsourcedentity.EventSourcedEntityContext;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the Event Sourced Entity Service described in your com/example/shoppingcart/shoppingcart_api.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShoppingCart extends AbstractShoppingCart {

  @SuppressWarnings("unused")
  private final String entityId;

  public ShoppingCart(EventSourcedEntityContext context) {
    this.entityId = context.entityId();
  }

  @Override
  public ShoppingCartDomain.Cart emptyState() {
      return ShoppingCartDomain.Cart.getDefaultInstance();
  }

  @Override
  public Effect<Empty> addItem(ShoppingCartDomain.Cart currentState, ShoppingCartApi.AddLineItem addLineItem) {
      if (addLineItem.getQuantity() <= 0) {
          return effects().error("Quantity for item " + addLineItem.getProductId() + " must be greater than zero.");
      }
      if(currentState.getCheckedOut()) {
          return effects().error("Cart already checked out.");
      }

      ShoppingCartDomain.ItemAdded event =
              ShoppingCartDomain.ItemAdded.newBuilder()
                      .setItem(
                              ShoppingCartDomain.LineItem.newBuilder()
                                      .setProductId(addLineItem.getProductId())
                                      .setName(addLineItem.getName())
                                      .setQuantity(addLineItem.getQuantity())
                                      .build())
                      .build();

      return effects()
              .emitEvent(event)
              .thenReply(newState -> Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> checkOutCart(ShoppingCartDomain.Cart currentState, ShoppingCartApi.CheckOutShoppingCart checkOutShoppingCart) {
      if(currentState.getCheckedOut()) {
          return effects().reply(Empty.getDefaultInstance());

      }else if(currentState.getItemsList().isEmpty()){
          return effects().error("Cart empty.");

      }else{
          ShoppingCartDomain.CheckedOut event =
                  ShoppingCartDomain.CheckedOut.newBuilder()
                          .setCheckedOutTimestamp(System.currentTimeMillis())
                          .build();

          return effects()
                  .emitEvent(event)
                  .thenReply(newState -> Empty.getDefaultInstance());
      }
  }


  @Override
  public Effect<ShoppingCartApi.Cart> getCart(ShoppingCartDomain.Cart currentState, ShoppingCartApi.GetShoppingCart getShoppingCart) {
      List<ShoppingCartApi.LineItem> apiItems =
              currentState.getItemsList().stream()
                      .map(this::convert)
                      .sorted(Comparator.comparing(ShoppingCartApi.LineItem::getProductId))
                      .collect(Collectors.toList());
      ShoppingCartApi.Cart apiCart =
              ShoppingCartApi.Cart.newBuilder().addAllItems(apiItems).build(); // <2>
      return effects().reply(apiCart);
  }

  private ShoppingCartApi.LineItem convert(ShoppingCartDomain.LineItem item) {
      return ShoppingCartApi.LineItem.newBuilder()
              .setProductId(item.getProductId())
              .setName(item.getName())
              .setQuantity(item.getQuantity())
              .build();
  }

  @Override
  public ShoppingCartDomain.Cart itemAdded(ShoppingCartDomain.Cart currentState, ShoppingCartDomain.ItemAdded itemAdded) {
      ShoppingCartDomain.LineItem item = itemAdded.getItem();
      return currentState.toBuilder().addItems(item).build();
  }

  @Override
  public ShoppingCartDomain.Cart checkedOut(ShoppingCartDomain.Cart currentState, ShoppingCartDomain.CheckedOut checkedOut) {
      return currentState.toBuilder().setCheckedOut(true).build();
  }

}
